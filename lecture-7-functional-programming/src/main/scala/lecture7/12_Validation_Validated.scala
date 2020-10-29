package lecture7

import cats.data.{Validated, ValidatedNel}

import scala.util.{Failure, Success, Try}
import cats.syntax.apply._
import cats.syntax.validated._

object ValidationValidated extends App {
  type ValidationResult[A] = ValidatedNel[DataError, A]

  def validateFirstName(name: String): ValidatedNel[DataError, String] = {
    if (name.length >= 2 && name.length <= 256) name.validNel
    else DataError("Name too short or too long").invalidNel
  }

  def validateSurname(name: String) = {
    if (name.length >= 2 && name.length <= 256) name.validNel
    else DataError("surname too short or too long").invalidNel
  }

  def validateAge(age: Option[String]) =
    age.map(i => Try(i.toInt)) match {
      case Some(value) => value match {
        case Failure(_) => DataError("invalid number").invalidNel
        case Success(value) if value >= 18 => value.validNel
        case _ => DataError("too young").invalidNel
      }
      case None => DataError("age is required").invalidNel
    }

  val data = RegistrationData("11", "b", None)


  val user = (
    validateFirstName(data.firstName).map(i => i),
    validateSurname(data.surname),
    validateAge(data.age)
    ).mapN((fname, sname, age) => User(fname, sname, age))


  val user2 = for {
    fname <- validateFirstName(data.firstName).toEither
    sname <- validateSurname(data.surname).toEither
    age <- validateAge(data.age).toEither
  } yield User(fname, sname, age)


  println(user)
  println(user2)
}
