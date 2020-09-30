// combinators

// foreach
Seq(1, 2, 3).foreach(x => println("elem " + x))

// map
Seq(1,2,3).map(_ * 2)

// Range
1 to 100
// map and then flatMap
Seq(3, 4, 5).map(x => (1 to x).toList)
Seq(3, 4, 5).flatMap(1 to _)

// filter odds
(1 to 30).toList.filter(_ % 2 == 0)

// collect multiply evens
(1 to 30).toList.collect{
  case x if x % 2 == 0 => x * 10
}