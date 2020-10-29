package lecture7

import scala.concurrent.Future
import scala.util.{Failure, Success, Try}


case class RegistrationData(firstName: String,
                            surname: String,
                            age: Option[String])

case class User(firstName: String,
                surname: String,
                age: Int)

case class DataError(msg: String) extends Exception(msg)


object Validation extends App {

  def validateFirstName(name: String): String = {
    if (name.length >= 2 && name.length <= 256) name
    else throw DataError("Name too short or too long")
  }

  def validateSurname(name: String): String = {
    if (name.length >= 2 && name.length <= 256) name
    else throw DataError("surname too short or too long")
  }

  def validateAge(age: Option[String]): Int =
    age.map(i => Try(i.toInt)) match {
      case Some(value) => value match {
        case Success(value) if value >= 18 => value
        case Failure(_) => throw DataError("invalid number")
        case _ => throw DataError("too young")
      }
      case None => throw DataError("age is required")
    }

  val data = RegistrationData("11", "bb", None)

  val user = {
    val fname = validateFirstName(data.firstName)
    val sname = validateSurname(data.surname)
    val age = validateAge(data.age)
    User(fname, sname, age)
  }

  println(user)

}


