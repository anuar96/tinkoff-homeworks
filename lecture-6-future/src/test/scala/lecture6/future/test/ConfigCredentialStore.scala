package lecture6.future.test

import com.typesafe.config.Config
import lecture6.future.store.AsyncCredentialStore
import net.ceedubs.ficus.Ficus._

import scala.concurrent.Future

class ConfigCredentialStore(config: Config) extends AsyncCredentialStore {
  private val credentialMap = config.as[Map[String, String]]("credentials")

  /**
    * возвращает хеш пользовательского пароля
    */
  override def find(user: String): Future[Option[String]] = ???
}