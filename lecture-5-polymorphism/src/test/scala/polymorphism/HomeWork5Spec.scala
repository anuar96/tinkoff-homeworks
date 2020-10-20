package polymorphism

import scala.collection.AbstractIterator

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

// * Для запуска тестов только в этом файле: `sbt testOnly *.HomeWork5Spec`
class HomeWork5Spec extends AnyFunSuite with Matchers {

  trait Ring[+T] extends Iterable[T]

  case class Salary(employee: String, amount: Double)

  class RingFromIterable[+T](val iterable: Iterable[T]) extends Ring[T] {
    override def iterator: Iterator[T] = new AbstractIterator[T] {
      var cursor = 0
      val list = iterable.iterator.toIndexedSeq

      def hasNext = list.nonEmpty

      def next(): T = {
        if (list.isDefinedAt(cursor)) {
          val elem = list(cursor)
          cursor = cursor + 1
          elem
        }
        else {
          cursor = 0
          val elem = list(cursor)
          cursor = cursor + 1
          elem
        }
      }
    }
  }

  object Ring {
    def apply[T](iterable: Iterable[T]): Ring[T] = new RingFromIterable[T](iterable)
  }

  trait XN[M[_]] {
    def x2[T](m: M[T]): M[T]

    def x3[T](m: M[T]): M[T]

    def x4[T](m: M[T]): M[T]
  }

  object XN {
    def apply[M[_]](implicit instance: XN[M]): XN[M] = instance

    implicit def xnSyntax[M[_] : XN, T](m: M[T]): XNOps[M, T] = new XNOps[M, T](m)

    final class XNOps[M[_] : XN, T](m: M[T]) {
      def x2: M[T] = XN[M].x2(m)

      def x3: M[T] = XN[M].x3(m)

      def x4: M[T] = XN[M].x4(m)
    }

    implicit val ringXN2: XN[Ring] = new XN[Ring] {
      override def x2[T](m: Ring[T]): Ring[T] = {
        new Ring[T] {
          override def iterator: Iterator[T] = new AbstractIterator[T] {
            var buffer = Seq.empty[T]

            def hasNext = !m.isEmpty

            def next(): T = {
              if (buffer.isEmpty) {
                val elem = iterator.next()
                buffer = Seq(elem, elem)
                elem
              }
              else {
                val elem = buffer.head
                buffer = buffer.tail
                elem
              }
            }
          }
        }
      }

      override def x3[T](m: Ring[T]): Ring[T] = new Ring[T] {
        override def iterator: Iterator[T] = new AbstractIterator[T] {
          var buffer = Seq.empty[T]

          def hasNext = !m.isEmpty

          def next(): T = {
            if (buffer.isEmpty) {
              val elem = iterator.next()
              buffer = Seq(elem, elem, elem)
              elem
            }
            else {
              val elem = buffer.head
              buffer = buffer.tail
              elem
            }
          }
        }
      }

      override def x4[T](m: Ring[T]): Ring[T] = new Ring[T] {
        override def iterator: Iterator[T] = new AbstractIterator[T] {
          var buffer = Seq.empty[T]

          def hasNext = !m.isEmpty

          def next(): T = {
            if (buffer.isEmpty) {
              val elem = iterator.next()
              buffer = Seq(elem, elem, elem)
              elem
            }
            else {
              val elem = buffer.head
              buffer = buffer.tail
              elem
            }
          }
        }
      }
    }
  }


  test("Кольцо состоит из зацикленных элементов начального списка") {

    Ring(Seq(1, 2, 3)).take(9).toSeq shouldBe Seq(1, 2, 3, 1, 2, 3, 1, 2, 3)

  }

  test("Ring расширен операцией xN") {
    import XN._

    val ring123 = Ring(Seq(1, 2, 3))

    Ring(ring123).x2.take(6).toSeq shouldBe Seq(1, 1, 2, 2, 3, 3)
    Ring(ring123).x3.take(9).toSeq shouldBe Seq(1, 1, 1, 2, 2, 2, 3, 3, 3)
    Ring(ring123).x4.take(12).toSeq shouldBe Seq(1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3)
  }
  /*
    test("Salary расширен операцией xN") {

      val bobSalary = Salary("Bob", 100.0)

      bobSalary.x2 shouldBe Salary("Bob", 200.0)
      bobSalary.x3 shouldBe Salary("Bob", 300.0)
      bobSalary.x4 shouldBe Salary("Bob", 400.0)

    }*/
}
