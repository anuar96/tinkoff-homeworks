//list

// default seq
Seq(1, 2, 3)
val list = List(1, 2, 3)

// head, tail
list.head
list.headOption
list.tail

// цепочка prepend
1 :: 2 :: 3 :: Nil
// :::
List(1, 2, 3) ::: List(4, 5, 6)

