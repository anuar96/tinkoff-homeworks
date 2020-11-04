package lecture8.assignment

import java.util.concurrent.{ExecutorService, Executors}

import org.scalatest.BeforeAndAfterAll
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.funspec.AnyFunSpec

import scala.concurrent.{ExecutionContext, ExecutionContextExecutor, Future}

class CoffeeMachineTest extends AnyFunSpec with BeforeAndAfterAll with ScalaFutures {
  implicit lazy val ec: ExecutionContextExecutor = ExecutionContext.fromExecutor(Executors.newFixedThreadPool(100))

  describe("Coffee machine") {
    it("should stop working after turning off") {
      val coffeeMachine = new CoffeeMachine

      def waitAndTurnOff = Future {
        Thread.sleep(100)
        coffeeMachine.turnOff()
      }

      Future(coffeeMachine.run())
        .zip(waitAndTurnOff)
        .futureValue
    }
  }
}
