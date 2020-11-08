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

  implicit val validatedParMappable: ParMappable[Validated] = new ParMappable[Validated] {
    override def map2[A, B, C](a: Validated[A], b: Validated[B])(f: (A, B) => C): Validated[C] = {
      (a, b) match {
        case (Valid(valueA), Valid(valueB)) => Validated.valid(f(valueA, valueB))
        case (Invalid(e1), Invalid(e2)) => Invalid(e1 ++ e2)
        case (Invalid(e), _) => Invalid(e)
        case (_, Invalid(e)) => Invalid(e)
      }
    }
    override def `new`[A](a: A): Validated[A] = Validated.valid(a)
  }

  implicit class cartesianMapN[F[_], A, B](tup: Tuple2[F[A], F[B]]) {
    def mapN[C](f: (A, B) => C)(implicit parMappable: ParMappable[F]): F[C] = {
      parMappable.map2(tup._1, tup._2)(f)
    }
  }
}
