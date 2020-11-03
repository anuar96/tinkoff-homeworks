package lecture7

import scala.util.{Failure, Success, Try}


object ValidationTry extends App {

  def validateFirstName(name: String): Try[String] = {
    if (name.length >= 2 && name.length <= 256) Success(name)
    else Failure(DataError("Name too short or too long"))
  }

  def validateSurname(name: String): Try[String] = {
    if (name.length >= 2 && name.length <= 256) Success(name)
    else Failure(DataError("surname too short or too long"))
  }

  def validateAge(age: Option[String]): Try[Int] = age.map(i => Try(i.toInt)) match {
    case Some(value) => value match {
      case Failure(_) => Failure(DataError("invalid number"))
      case Success(value) if value >= 18 => Success(value)
      case _ => Failure(DataError("too young"))
    }
    case None => Failure(DataError("age is required"))
  }

  val data = RegistrationData("11", "bn", Some("20"))

  val user = for {
    fname <- validateFirstName(data.firstName)
    sname <- validateSurname(data.surname)
    age <- validateAge(data.age)
  } yield User(fname, sname, age)

  println(user)

}


