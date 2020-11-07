package lecture8.assignment

import java.util.concurrent.Executors

import org.scalatest.concurrent.ScalaFutures
import org.scalatest.funspec.AnyFunSpec

import scala.concurrent.{ExecutionContext, ExecutionContextExecutor, Future}
import scala.util.Random

class StudentBuilderTest extends AnyFunSpec with ScalaFutures {
  implicit lazy val ec: ExecutionContextExecutor = ExecutionContext.fromExecutor(Executors.newFixedThreadPool(10))

  describe("student builder") {
    it("should build students") {
      val student = new StudentBuilder()
        .withAge(18)

      val names = Seq("John", "Brus", "Superman", "Alexey", "Ola", "Ksenia")
      val expected = (10 to 100).map { age => Student(names(Random.nextInt(names.size)), age) }.toSet

      val actual = Future.traverse(expected) { s =>
        for {
          withName <- Future(student.withName(s.name))
          withAge <- Future(withName.withAge(s.age))
          student <- Future(withAge.buildOrThrow())
        } yield student
      }.futureValue

      assert(expected.forall(actual.contains))
    }
  }
}
