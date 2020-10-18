package lecture6.typeclass

import java.util.concurrent.CompletableFuture

import org.scalactic.Prettifier
import org.scalactic.source.Position

import scala.concurrent.ExecutionException
import scala.util.{Failure, Try}

class RecoverableCompletableFutureTest extends RecoverableTest[CompletableFuture] {
  override def assertEqualsF[L, R](left: CompletableFuture[L],
                                   right: CompletableFuture[R])
                                  (implicit prettifier: Prettifier, pos: Position): Unit = {
    (Try(left.get()), Try(right.get())) match { // Упрощенная реализация с блокировками
      case (Failure(le: ExecutionException), Failure(re: ExecutionException)) =>
        assert(le.getCause == re.getCause)
      case (l, r) => assert(l == r)
    }
  }
}
