// for

// foreach println (without yield)
for {
  l1 <- List(1, 2, 3)
} println(l1)

// for Seq, Set, Option
for {
  x <- Seq(1, 2)
  y <- Set(3, 4)
  z <- Option(5)
} yield (x, y, z)

