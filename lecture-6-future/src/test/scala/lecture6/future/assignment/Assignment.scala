package lecture6.future.assignment

import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContext, Future, Promise, TimeoutException}
import scala.util.{Failure, Success, Try}

import com.github.t3hnar.bcrypt._
import com.typesafe.scalalogging.StrictLogging
import lecture6.future.bcrypt.AsyncBcrypt
import lecture6.future.store.AsyncCredentialStore
import lecture6.future.util.Scheduler

class Assignment(bcrypt: AsyncBcrypt, credentialStore: AsyncCredentialStore)
                (implicit executionContext: ExecutionContext) extends StrictLogging {

  /**
   * проверяет пароль для пользователя
   * возвращает Future со значением false:
   *   - если пользователь не найден
   *   - если пароль не подходит к хешу
   */
  def verifyCredentials(user: String, password: String): Future[Boolean] = {
    for {
      maybeStoredHash: Option[String] <- credentialStore.find(user)
      pass: Boolean <- maybeStoredHash match {
        case Some(storedHash) => bcrypt.verify(password, storedHash)
        case None => Future.successful[Boolean](false)
      }
    } yield {
      pass
    }
  }

  /**
   * выполняет блок кода, только если переданы верные учетные данные
   * возвращает Future c ошибкой InvalidCredentialsException, если проверка не пройдена
   */
  def withCredentials[A](user: String, password: String)(block: => A): Future[A] = {
    verifyCredentials(user, password).flatMap { isPassed: Boolean =>
      if (isPassed)
        Future.successful(block)
      else
        Future.failed(new InvalidCredentialsException)
    }
  }


  /**
   * хеширует каждый пароль из списка и возвращает пары пароль-хеш
   */
  def hashPasswordList(passwords: Seq[String]): Future[Seq[(String, String)]] = {
    Future.sequence(passwords.map { password =>
      bcrypt.hash(password).map((password, _))
    })
  }


  /**
   * проверяет все пароли из списка против хеша, и если есть подходящий - возвращает его
   * если подходит несколько - возвращается любой
   */
  def findMatchingPassword(passwords: Seq[String], hash: String): Future[Option[String]] = {
    passwords.foldLeft(Future.successful(None): Future[Option[String]]){
      case (matchingPassword, password: String) =>
        matchingPassword.flatMap{
          case None =>
            bcrypt.verify(password, hash).map{isVerified =>
              if (isVerified)
                Some(password)
              else
                None
            }
          case Some(m) => Future.successful(Some(m))
        }
    }
  }

  /**
   * логирует начало и окончание выполнения Future, и продолжительность выполнения
   */
  def withLogging[A](tag: String)(f: => Future[A]): Future[A] = {
    logger.debug(s"$tag begin")
    val beginTime = java.lang.System.currentTimeMillis()
    f.map { res =>
      val duration = java.lang.System.currentTimeMillis() - beginTime
      logger.debug(s"$tag duration is $duration")
      logger.debug(s"$tag end")
      res
    }
  }

  /**
   * пытается повторно выполнить f retries раз, до первого успеха
   * если все попытки провалены, возвращает первую ошибку
   *
   * Важно: f не должна выполняться большее число раз, чем необходимо
   */
  def withRetry[A](f: => Future[A], retries: Int): Future[A] = {
    def go(f: => Future[A], retries: Int): Future[A] = {
      val future: Future[A] = f
      if (retries == 1) future
      else future.recoverWith {
        case t: Throwable =>
          go(f, retries - 1).recoverWith { case _ =>
            Future.failed(t)
          }
      }
    }

    go(f, retries)
  }

  /**
   * по истечению таймаута возвращает Future.failed с java.util.concurrent.TimeoutException
   */
  def withTimeout[A](f: Future[A], timeout: FiniteDuration): Future[A] = {
    val promise = Promise[A]
    promise.completeWith(f)
    Scheduler.executeAfter(timeout){
      promise.failure(new TimeoutException)
    }
    promise.future
  }

  /**
   * делает то же, что и hashPasswordList, но дополнительно:
   *   - каждая попытка хеширования отдельного пароля выполняется с таймаутом
   *   - при ошибке хеширования отдельного пароля, попытка повторяется в пределах retries (свой на каждый пароль)
   *   - возвращаются все успешные результаты
   */
  def hashPasswordListReliably(passwords: Seq[String], retries: Int, timeout: FiniteDuration): Future[Seq[(String, String)]] = {
    def futureToFutureTry[T](f: Future[T]): Future[Try[T]] =
      f.map(Success(_)).recover { case x => Failure(x)}

    val seqOfHashes: Seq[Future[(String, String)]] = passwords.map { password =>
      withRetry(withTimeout(bcrypt.hash(password).map((password, _)),
        timeout), retries)
    }

    val seqOfHashesTry = seqOfHashes.map(futureToFutureTry)

    Future.sequence(seqOfHashesTry).map(_.collect{
      case Success(value) => value
    })
  }
}
