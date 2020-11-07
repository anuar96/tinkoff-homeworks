package lecture8.assignment

import java.util.concurrent.Executors

import org.scalatest.concurrent.ScalaFutures
import org.scalatest.funspec.AnyFunSpec

import scala.concurrent.{ExecutionContext, ExecutionContextExecutor, Future}

class BagTest extends AnyFunSpec with ScalaFutures {
  implicit lazy val ec: ExecutionContextExecutor = ExecutionContext.fromExecutor(Executors.newFixedThreadPool(10))

  describe("Bag") {
    it("should contains 1000 things") {
      val bag = new Bag
      Future.traverse(1 to 1000){ i => Future(bag.putThing(s"thing for ${i}", i.toDouble))}
        .futureValue

      assert(bag.size == 1000)
    }
  }

}
