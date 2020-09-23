// value match

def printer1(i: Int) = i match {
  case 1 => "One"
  case 2 => "Two"
}

printer1(1)
printer1(2)

//printer1(3)

// other case (variable pattern)
def printer2(i: Int) = i match {
  case 1 => "One"
  case 2 => "Two"
  case other => "unknown number: " + other
}

printer2(1)

// constants reference

val One = 1

def printer3(i: Int) = i match {
  case One => "One"
  case 2 => "Two"
  case other => "unknown number: " + other
}

printer3(1)

// lowercase capital variable reference
val one = 1

def printer4(i: Int) = i match {
  case `one` => "One"
  case 2 => "Two"
  case other => "unknown number: " + other
}

printer4(1)