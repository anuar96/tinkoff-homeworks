package lecture6.future.store

import scala.concurrent.Future

trait AsyncCredentialStore {
  /**
    * возвращает хеш пользовательского пароля
    */
  def find(user: String): Future[Option[String]]
}
