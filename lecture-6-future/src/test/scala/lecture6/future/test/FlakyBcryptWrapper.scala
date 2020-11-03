package lecture6.future.test

import java.util.concurrent.ThreadLocalRandom

import lecture6.future.bcrypt.AsyncBcrypt

import scala.concurrent.{ExecutionContext, Future, Promise}
import scala.util.Random._

class FlakyBcryptWrapper(bcrypt: AsyncBcrypt) extends AsyncBcrypt {
  private object Action extends Enumeration {
    val Normal, Recurse, Fail, Hang = Value
  }
  import Action._

  private def random = ThreadLocalRandom.current()

  private def randomAction: Action.Value = {
    val randomValue = random.nextDouble()
    if (randomValue < 0.3) Normal
    else if (randomValue < 0.8) Recurse
    else if (randomValue < 0.9) Fail
    else Hang
  }

  override def hash(password: String, rounds: Int)(implicit executionContext: ExecutionContext): Future[String] =
    randomAction match {
      case Normal => bcrypt.hash(password, rounds)
      case Recurse => bcrypt.hash(random.nextString(10), rounds).flatMap(_ => hash(password, rounds))
      case Fail => Future.failed(new IllegalStateException("Random failure"))
      case Hang => Promise[String]().future
    }

  override def verify(password: String, hash: String)(implicit executionContext: ExecutionContext): Future[Boolean] =
    randomAction match {
      case Normal => bcrypt.verify(password, hash)
      case Recurse => bcrypt.verify(random.nextString(10), hash).flatMap(_ => verify(password, hash))
      case Fail => Future.failed(new IllegalStateException("Random failure"))
      case Hang => Promise[Boolean]().future
    }
}
