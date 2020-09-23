// Extractors

object HasThreeWords {
  def unapply(arg: String): Boolean = arg.split("\\s+").length == 3
}

"Возможно это фамилия" match {
  case  HasThreeWords() => true
  case _ => false
}

"это  фамилия" match {
  case  HasThreeWords() => true
  case _ => false
}


object FIO {
  def unapply(fio: String): Option[(String, String, String)] = {
    fio.split("\\s+") match {
      case Array(firstName, lastName, coName) =>
        Some(firstName, lastName, coName)
      case _ => None
    }

  }
}

"Кузнецов Олег Георгиевич" match {
  case FIO(f, l, c) => Array(f, l, c)
}


object Words {
  def unapplySeq(words: String): Option[Seq[String]] = {
    val splitted = words.split("\\s+").toSeq
    if (splitted.nonEmpty) Some(splitted)
    else None
  }
}

"a b c d" match {
  case Words(a, b) => a + b
  case Words(a, b, c, d) => d + c + b + a
  case _ => "not found"
}


// FindNumber regexp match

val FindNumber = """(.*?)(\d+)(.*?)""".r


"somesdss123665cnsdk" match {
  case FindNumber(left, num, right) =>
    println(num)
    println(left)
    println(right)
}

// <=