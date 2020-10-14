package lecture6.future.assignment

import com.typesafe.scalalogging.StrictLogging
import lecture6.future.bcrypt.AsyncBcrypt
import lecture6.future.store.AsyncCredentialStore

import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.duration.FiniteDuration

class Assignment(bcrypt: AsyncBcrypt, credentialStore: AsyncCredentialStore)
                (implicit executionContext: ExecutionContext) extends StrictLogging {

  /**
    * проверяет пароль для пользователя
    * возвращает Future со значением false:
    *   - если пользователь не найден
    *   - если пароль не подходит к хешу
    */
  def verifyCredentials(user: String, password: String): Future[Boolean] =
    ???

  /**
    * выполняет блок кода, только если переданы верные учетные данные
    * возвращает Future c ошибкой InvalidCredentialsException, если проверка не пройдена
    */
  def withCredentials[A](user: String, password: String)(block: => A): Future[A] =
    ???

  /**
    * хеширует каждый пароль из списка и возвращает пары пароль-хеш
    */
  def hashPasswordList(passwords: Seq[String]): Future[Seq[(String, String)]] =
    ???

  /**
    * проверяет все пароли из списка против хеша, и если есть подходящий - возвращает его
    * если подходит несколько - возвращается любой
    */
  def findMatchingPassword(passwords: Seq[String], hash: String): Future[Option[String]] =
    ???

  /**
    * логирует начало и окончание выполнения Future, и продолжительность выполнения
    */
  def withLogging[A](tag: String)(f: => Future[A]): Future[A] =
    ???

  /**
    * пытается повторно выполнить f retries раз, до первого успеха
    * если все попытки провалены, возвращает первую ошибку
    *
    * Важно: f не должна выполняться большее число раз, чем необходимо
    */
  def withRetry[A](f: => Future[A], retries: Int): Future[A] =
    ???

  /**
    * по истечению таймаута возвращает Future.failed с java.util.concurrent.TimeoutException
    */
  def withTimeout[A](f: Future[A], timeout: FiniteDuration): Future[A] =
    ???

  /**
    * делает то же, что и hashPasswordList, но дополнительно:
    *   - каждая попытка хеширования отдельного пароля выполняется с таймаутом
    *   - при ошибке хеширования отдельного пароля, попытка повторяется в пределах retries (свой на каждый пароль)
    *   - возвращаются все успешные результаты
    */
  def hashPasswordListReliably(passwords: Seq[String], retries: Int, timeout: FiniteDuration): Future[Seq[(String, String)]] =
    ???
}
