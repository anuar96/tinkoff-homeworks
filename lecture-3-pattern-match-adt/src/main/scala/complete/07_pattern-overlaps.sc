// pattern overlaps

def matcher(i: Double): Unit = i match {
  case i if i > 0 => println(0)
  case i if i > 1 => println(1)
  case i if i > 2 => println(2)
}

matcher(1)
matcher(2)


// <=