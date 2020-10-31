package polymorphism

import scala.util.{Success, Try}

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
    def xn(m: M, n : Int): Try[M] = Try{
      n match{
        case 2 => x2(m)
        case 3 => x3(m)
        case 4 => x4(m)
      }
    }

    def x2(m: M): M

    def x3(m: M): M

    def x4(m: M): M
  }

  object XN {
    def apply[M](implicit instance: XN[M]): XN[M] = instance

    implicit def xnSimpleSyntax[M: XN](m: M): XNSimpleOps[M] = new XNSimpleOps[M](m)

    final class XNSimpleOps[M: XN](m: M) {
      def xN(n: Int) = XN[M].xn(m, n)

      def x2: M = XN[M].x2(m)

      def x3: M = XN[M].x3(m)

      def x4: M = XN[M].x4(m)
    }

    implicit def xnSimple[T]: XN[Ring[T]] = new XN[Ring[T]] {
      override def x2(m: Ring[T]): Ring[T] = new Ring[T] {
        override def iterator: Iterator[T] = m.iterator.flatMap { elem => Iterator(elem, elem) }
      }

      override def x3(m: Ring[T]): Ring[T] = new Ring[T] {
        override def iterator: Iterator[T] = m.iterator.flatMap { elem => Iterator(elem, elem, elem) }
      }

      override def x4(m: Ring[T]): Ring[T] = new Ring[T] {
        override def iterator: Iterator[T] = m.iterator.flatMap { elem => Iterator(elem, elem, elem, elem) }
      }
    }

    implicit val salaryXN: XN[Salary] = new XN[Salary] {
      override def x2(m: Salary): Salary = Salary(m.employee, m.amount * 2)

      override def x3(m: Salary): Salary = Salary(m.employee, m.amount * 3)

      override def x4(m: Salary): Salary = Salary(m.employee, m.amount * 4)
    }
  }

  test("Кольцо состоит из зацикленных элементов начального списка") {
    Ring(Seq(1, 2, 3)).take(9).toSeq shouldBe Seq(1, 2, 3, 1, 2, 3, 1, 2, 3)
  }

  test("Ring расширен операцией xN") {
    import XN._

    val ring123 = Ring(Seq(1, 2, 3))

    Ring(ring123).x2.take(6).toSeq shouldBe Seq(1, 1, 2, 2, 3, 3)
    Ring(ring123).xN(2).map(_.take(6).toSeq) shouldBe Success(Seq(1, 1, 2, 2, 3, 3))
    Ring(ring123).x3.take(9).toSeq shouldBe Seq(1, 1, 1, 2, 2, 2, 3, 3, 3)
    Ring(ring123).x4.take(12).toSeq shouldBe Seq(1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3)
  }

  test("Salary расширен операцией xN") {
    import XN._

    val bobSalary: Salary = Salary("Bob", 100.0)

    bobSalary.x2 shouldBe Salary("Bob", 200.0)
    bobSalary.x3 shouldBe Salary("Bob", 300.0)
    bobSalary.x4 shouldBe Salary("Bob", 400.0)
    bobSalary.xN(2) shouldBe Success(Salary("Bob", 200.0))

  }
}
