// map

// apply, get
val map = Map("a" -> 1, "b" -> 2)

map("a")
//map("aa")
map.get("a")

// groupBy, "some some" string, event&odd
"Some Some".toSeq.groupBy(identity)
(1 to 10).groupBy(_ % 2 == 0)
