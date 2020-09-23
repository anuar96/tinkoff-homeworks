// case class


sealed trait Size
case object Big extends Size
case object Small extends Size
// serializable, hash, toString

case class Soup(name: String, temperature: Double)
case class Bowl(soup: Soup, size: Size)


(Small: Size) match {
  case Small => 0
}

Bowl(Soup("chowder", 40.0), Big) match {
  case Bowl(Soup(_, t), size@(Big | Small)) if t > 30 => (t, size)
}

object Size2 extends Enumeration {
  type Size = Value
  val Small, Big = Value
}

Size2.Small.id
Size2.values

(Size2.Small: Size2.Value) match {
  case Size2.Small => "Small"
}

// homework. Look Enumeratum Luke
