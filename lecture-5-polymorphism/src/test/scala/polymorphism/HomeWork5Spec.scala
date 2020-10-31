package polymorphism


import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

// * Для запуска тестов только в этом файле: `sbt testOnly *.HomeWork5Spec`
class HomeWork5Spec extends AnyFunSuite with Matchers {

  trait Ring[+T] extends Iterable[T]

  case class Salary(employee: String, amount: Double)

  class RingFromIterable[+T](val iterable: Iterable[T]) extends Ring[T] {
    override def iterator: Iterator[T] = Iterator.continually(iterable).flatten
  }

  object Ring {
    def apply[T](iterable: Iterable[T]): Ring[T] = new RingFromIterable[T](iterable)
  }

  trait XN[M] {
    def xN(m: M, n: Int): M

    def x2(m: M): M = xN(m, 2)

    def x3(m: M): M = xN(m, 3)

    def x4(m: M): M = xN(m, 4)
  }

  object XN {
    def apply[M](implicit instance: XN[M]): XN[M] = instance

    implicit def xnSimpleSyntax[M: XN](m: M): XNSimpleOps[M] = new XNSimpleOps[M](m)

    final class XNSimpleOps[M: XN](m: M) {
      def xN(n: Int): M = XN[M].xN(m, n)

      def x2: M = XN[M].x2(m)

      def x3: M = XN[M].x3(m)

      def x4: M = XN[M].x4(m)
    }

    implicit def xnSimple[T]: XN[Ring[T]] = new XN[Ring[T]] {
      override def xN(m: Ring[T], n: Int): Ring[T] = new Ring[T]{
        override def iterator: Iterator[T] = m.iterator.flatMap { elem =>
          Iterator(List.fill(n)(elem): _*)
        }
      }
    }

    implicit val salaryXN: XN[Salary] = new XN[Salary] {
      override def xN(m: Salary, n: Int): Salary = Salary(m.employee, m.amount * n)
    }
  }

  test("Кольцо состоит из зацикленных элементов начального списка") {
    Ring(Seq(1, 2, 3)).take(9).toSeq shouldBe Seq(1, 2, 3, 1, 2, 3, 1, 2, 3)
  }

  test("Ring расширен операцией xN") {
    import XN._

    val ring123 = Ring(Seq(1, 2, 3))

    Ring(ring123).x2.take(6).toSeq shouldBe Seq(1, 1, 2, 2, 3, 3)
    Ring(ring123).xN(-2).take(6).toSeq shouldBe Seq(1, 1, 2, 2, 3, 3)
    Ring(ring123).x3.take(9).toSeq shouldBe Seq(1, 1, 1, 2, 2, 2, 3, 3, 3)
    Ring(ring123).x4.take(12).toSeq shouldBe Seq(1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3)
  }

  test("Salary расширен операцией xN") {
    import XN._

    val bobSalary: Salary = Salary("Bob", 100.0)

    bobSalary.x2 shouldBe Salary("Bob", 200.0)
    bobSalary.x3 shouldBe Salary("Bob", 300.0)
    bobSalary.x4 shouldBe Salary("Bob", 400.0)
    bobSalary.xN(2) shouldBe Salary("Bob", 200.0)
  }
}
