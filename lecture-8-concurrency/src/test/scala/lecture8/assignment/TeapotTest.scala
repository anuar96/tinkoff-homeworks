package lecture8.assignment

import java.util.concurrent.Executors

import org.scalatest.concurrent.ScalaFutures
import org.scalatest.funspec.AnyFunSpec

import scala.concurrent.{ExecutionContext, ExecutionContextExecutor, Future}
import scala.util.Random

class TeapotTest extends AnyFunSpec with ScalaFutures {
  implicit lazy val ec: ExecutionContextExecutor = ExecutionContext.fromExecutor(Executors.newFixedThreadPool(10))

  describe("teapot") {
    it("should return status of teapot") {
      val teapot = new Teapot

      Future.traverse(1 to 100) { _ =>
        Future {
          if (Random.nextBoolean()) {
            teapot.turnOff()
          } else teapot.turnOn()
        }.zip(Future(teapot.getStatus))
      }.futureValue
    }
  }
}
