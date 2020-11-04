package lecture8.assignment

import java.util.concurrent.Executors

import org.scalatest.concurrent.{PatienceConfiguration, ScalaFutures}
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.time.Span

import scala.concurrent.duration.DurationInt
import scala.concurrent.{ExecutionContext, ExecutionContextExecutor, Future}

class CoronaVisitorGuardTest extends AnyFunSpec with ScalaFutures {
  implicit val ec: ExecutionContextExecutor =
    ExecutionContext.fromExecutor(Executors.newFixedThreadPool(100))



  describe("Visitors guard") {
    it("should limit visitors count") {
      val maxVisitors = 500
      val guard = new CoronaVisitorGuard(maxVisitors)

      Future.traverse(1 to 1000)(_ => Future { guard.tryEnter() }).futureValue

      println(guard.getCurrentVisitorsCount())
      assert(guard.getCurrentVisitorsCount() == maxVisitors)
    }
  }

  override implicit def patienceConfig: PatienceConfig = PatienceConfig(2.hours)
}
