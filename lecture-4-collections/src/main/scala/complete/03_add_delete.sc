import scala.collection.mutable

// add

// seq associativity +
Seq(1) :+ 2
2 +: Seq(1)
// цепочка prepend
1 +: 2 +: 3 +: Seq.empty

// seq ++
Seq(1) ++ Seq(2,3)

// set, map +, ++
Set(1) + 2
Set(1) ++ Set(2, 3)
Map(1 -> "a") ++ Map(2 -> "b", 3 -> "c")

// set -, --
Set(2) - 2
Set(1, 2, 3) -- Set(1, 2)

// set, map +=, ++=
var s1 = Set(1,2)
s1 += 3
s1 ++= Set(4, 5)
s1

var m1 = Map(1 -> "a")
m1 += (2 -> "b")
m1 ++= Map(3 -> "c", 4 -> "d")
m1

// mutable +=, ++=
val s2 = mutable.Set(1,2)
s2 += 3
s2 ++= Set(4, 5)

val m2 = mutable.Map(1 -> "a")
m2 += (2 -> "b")
m2 ++= Map(3 -> "c", 4 -> "d")
