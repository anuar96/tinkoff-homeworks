package crutch

import org.scalatest.enablers.Emptiness
import org.scalatest.matchers.should.Matchers

/**
 * Костыли, для того чтобы компилировался такой код:
 * {{{
 *   val seq = ???
 *   seq shouldBe a[Seq[_]]
 * }}}
 * Идея Miles Sabin: https://github.com/scala/bug/issues/9453
 * Корневая проблема компилятора: https://github.com/scala/bug/issues/1570
 */
trait NothingFixes {
  this: Matchers =>

  type ReallyNothing = Nothing { type T = Unit }
  def ??? : ReallyNothing = scala.Predef.???
  implicit def nothingShouldFix(n: ReallyNothing): AnyShouldWrapper[Nothing] = ???
  implicit def emptyness[T]: Emptiness[Array[T]] = org.scalatest.enablers.Emptiness.emptinessOfArray[T]
}
