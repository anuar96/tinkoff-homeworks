package lecture6.typeclass

import org.scalactic.Prettifier
import org.scalactic.source.Position

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, Future}
import scala.util.Try

class RecoverableFutureTest extends RecoverableTest[Future] {
  override def assertEqualsF[L, R](left: Future[L], right: Future[R])
                                  (implicit prettifier: Prettifier, pos: Position): Unit = {
    assert(Try(Await.result(left, 10.seconds)) == Try(Await.result(right, 10.seconds))) // Await тут для простоты
  }
}
