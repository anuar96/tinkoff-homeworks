package lecture6.future.assignment

import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContext, Future}

import com.typesafe.scalalogging.StrictLogging
import lecture6.future.bcrypt.AsyncBcrypt
import lecture6.future.store.AsyncCredentialStore

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
      pass: Boolean <- maybeStoredHash match{
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
    verifyCredentials(user, password).flatMap { isPassed =>
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
    for {
      hashPasswordList <- hashPasswordList(passwords)
    } yield {
      hashPasswordList.collectFirst {
        case (password, passwordHash) if passwordHash == hash => password
      }
    }
  }

  /**
   * логирует начало и окончание выполнения Future, и продолжительность выполнения
   */
  def withLogging[A](tag: String)(f: => Future[A]): Future[A] = {
    logger.debug(s"$f begin")
    val beginTime = java.lang.System.currentTimeMillis()
    f.map { res =>
      val duration = java.lang.System.currentTimeMillis() - beginTime
      logger.debug(s"$f duration is $duration")
      logger.debug(s"$f end")
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
      val future = f
      if (retries == 0) future else future.fallbackTo(go(future, retries - 1))
    }

    go(f, retries)
  }

  /**
   * по истечению таймаута возвращает Future.failed с java.util.concurrent.TimeoutException
   */
  def withTimeout[A](f: Future[A], timeout: FiniteDuration): Future[A] = {
    Future(Await.result(f, timeout))
  }


  /**
   * делает то же, что и hashPasswordList, но дополнительно:
   *   - каждая попытка хеширования отдельного пароля выполняется с таймаутом
   *   - при ошибке хеширования отдельного пароля, попытка повторяется в пределах retries (свой на каждый пароль)
   *   - возвращаются все успешные результаты
   */
  def hashPasswordListReliably(passwords: Seq[String], retries: Int, timeout: FiniteDuration): Future[Seq[(String, String)]] = {
    hashPasswordList(passwords)
    Future.sequence(passwords.map { password =>
      withRetry(withTimeout(bcrypt.hash(password).map((password, _)), 10 seconds), 5)
    })
  }
}
