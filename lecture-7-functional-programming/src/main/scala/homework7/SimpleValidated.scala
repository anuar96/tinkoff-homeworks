package homework7


sealed trait Validated[+A]
case class Invalid(error: List[Exception]) extends Validated[Nothing]
case class Valid[+A](value: A) extends Validated[A]

object Validated {
  def valid[A](a: A): Validated[A] = Valid(a)
  def invalid[A](e: Exception): Validated[A] = Invalid(List(e))
}

trait ParMappable[F[_]] {
  def map2[A, B, C](a: F[A], b: F[B])(f: (A, B) => C): F[C]
  def `new`[A](a: A): F[A]
}

object ParMappable {

  // TODO
  implicit val validatedParMappable: ParMappable[Validated] = new ParMappable[Validated] {
    override def map2[A, B, C](a: Validated[A], b: Validated[B])(f: (A, B) => C): Validated[C] = ??? // TODO

    override def `new`[A](a: A): Validated[A] = ??? // TODO
  }

  // TODO
  implicit class cartesianMapN(val a: Nothing)
}
