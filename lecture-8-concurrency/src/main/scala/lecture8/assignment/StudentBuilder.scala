package lecture8.assignment

case class StudentBuilder(name: Option[String],
                          age: Option[Int]) {

  def this() =
    this(None, None)

  def withName(name: String): StudentBuilder = {
    copy(name = Some(name))
  }

  def withAge(age: Int): StudentBuilder = {
    copy(age = Some(age))
  }

  def buildOrThrow(): Student = {
    (for {
      n <- name
      a <- age
    } yield Student(n, a))
      .getOrElse(throw new IllegalStateException("Required field missing"))
  }
}

case class Student(name: String, age: Int)


