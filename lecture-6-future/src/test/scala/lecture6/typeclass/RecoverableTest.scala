package lecture6.typeclass

import org.scalactic.Prettifier
import org.scalactic.source.Position
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

abstract class RecoverableTest[F[_]](implicit F: Recoverable[F]) extends AnyFunSuite {
  import lecture6.typeclass.Recoverable.RecoverableOps

  def assertEqualsF[L, R](left: F[L], right: F[R])(implicit prettifier: Prettifier, pos: Position)

  test("'new'.map(identity)  <-> 'new' ") {
    assertEqualsF(fa.map(identity), fa)
  }

  test("fa.flatMap(f).flatMap(g) <-> fa.flatMap(a => f(a).flatMap(g))") {
    def f(i: Int) = F.`new`(i * 3)
    def g(i: Int) = F.`new`(i * 4)

    assertEqualsF(fa.flatMap(f).flatMap(g), fa.flatMap(a => f(a).flatMap(g)))
  }

  test("'new'.flatMap(error) <-> raiseError(error)") {
    val exception = new RuntimeException

    assertEqualsF(fa.flatMap(_ => F.raiseError[Int](exception)), F.raiseError[Int](exception))
  }

  test("raiseError(e).recover(value) <-> 'new'") {
    assertEqualsF(F.raiseError[Int](new RuntimeException).recover { case _ => a}, fa)
  }

  test("raiseError(e).recoverWith('new') <-> 'new'") {
    assertEqualsF(F.raiseError[Int](new RuntimeException).recoverWith { case _ => fa}, fa)
  }

  test("'new'.recoverWith(throw error) <-> 'new'") {
    assertEqualsF(fa.recover { case _ => throw new RuntimeException }, fa)
  }

  private val a = 10
  private def fa: F[Int] = F.`new`(a)
}




