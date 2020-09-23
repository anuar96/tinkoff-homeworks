// Pattern variables

val (name, salary) = ("Name", 1100.0)

println(name)

println(salary)

case class Account(money: Double)

val ac = Account(100)

val Account(money) = ac

println(money)

object Words {
  def unapplySeq(words: String): Option[Seq[String]] = {
    val splitted = words.split("\\s+").toSeq
    if (splitted.nonEmpty) Some(splitted)
    else None
  }
}

val Words(f, l, c) =  "Кузнецов Олег Георгиевич"

println(f)
println(l)
println(c)


val List(a,b,c) = List(1,2,3)

// <=