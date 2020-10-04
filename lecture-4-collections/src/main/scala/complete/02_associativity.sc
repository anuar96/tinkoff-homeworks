// associativity example

// :@@@
case class Assoc(name: String) {
  def :@@@(that: Assoc): String = s"$name + ${that.name} Left associative"
  def @@@:(that: Assoc): String = s"${that.name} + $name Right associative"
}


Assoc("A") :@@@ Assoc("B")
Assoc("A").:@@@(Assoc("B"))
Assoc("A") @@@: Assoc("B")
Assoc("B").@@@:(Assoc("A"))



