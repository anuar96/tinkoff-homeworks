package polymorphism

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

/**
 * Для запуска тестов только в этом файле: `sbt testOnly *.HomeWork5Spec`
 */
class HomeWork5Spec extends AnyFunSuite with Matchers {

  trait Ring[+T] extends Iterable[T]
  case class Salary(employee: String, amount: Double)

  class RingFromIterable[+T](iterable: Iterable[T]) extends Ring[T] {
    override def iterator: Iterator[T] = ???
  }

  object Ring {
    def apply[T](iterable: Iterable[T]): Ring[T] = new RingFromIterable[T](iterable)
  }

  trait XN[M] {
    def x2(m: M): M
    def x3(m: M): M
    def x4(m: M): M
  }


  test("Кольцо состоит из зацикленных элементов начального списка") {

    Ring(Seq(1, 2, 3)).take(9).toSeq shouldBe Seq(1, 2, 3, 1, 2, 3, 1, 2, 3)

  }

  test("Ring расширен операцией xN") {

    val ring123 = Ring(Seq(1, 2, 3))

    Ring(ring123).x2.take(6).toSeq shouldBe Seq(1, 1,  2, 2, 3, 3)
    Ring(ring123).x3.take(9).toSeq shouldBe Seq(1, 1, 1, 2, 2, 2, 3, 3, 3)
    Ring(ring123).x4.take(12).toSeq shouldBe Seq(1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3)
  }

  test("Salary расширен операцией xN") {

    val bobSalary = Salary("Bob", 100.0)

    bobSalary.x2 shouldBe Salary("Bob", 200.0)
    bobSalary.x3 shouldBe Salary("Bob", 300.0)
    bobSalary.x4 shouldBe Salary("Bob", 400.0)

  }

}