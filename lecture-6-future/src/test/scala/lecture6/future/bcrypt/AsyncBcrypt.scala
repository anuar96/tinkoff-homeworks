package lecture6.future.bcrypt

import com.github.t3hnar.bcrypt.BCryptStrOps
import com.typesafe.scalalogging.StrictLogging
import lecture6.future.util.ExecutionLogging

import scala.concurrent.{ExecutionContext, Future, blocking}

trait AsyncBcrypt {

  def hash(password: String, rounds: Int = 12)(implicit executionContext: ExecutionContext): Future[String]

  def verify(password: String, hash: String)(implicit executionContext: ExecutionContext): Future[Boolean]

}

class AsyncBcryptImpl extends AsyncBcrypt with StrictLogging with ExecutionLogging {

  override def hash(password: String, rounds: Int)
                   (implicit executionContext: ExecutionContext): Future[String] =
    Future {
      withExecutionLogging(s"hashing $password")(blocking(password.bcrypt(rounds)))
    }

  override def verify(password: String, hash: String)
                     (implicit executionContext: ExecutionContext): Future[Boolean] =
    Future {
      withExecutionLogging(s"verifying $password")(blocking(password.isBcrypted(hash)))
    }

}