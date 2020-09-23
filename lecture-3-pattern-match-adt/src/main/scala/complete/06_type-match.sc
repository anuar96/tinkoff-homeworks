// type match

def test(par: Any) = par match {
  case s: String => "String " + s
  case n: Number => "Number " + n
  case b: Boolean => "Boolean " + b
  case other => "Other " + other.getClass.toString
}

test("Hello")
test(111)
test(1.0)
test(true)

// type erasure
// Option -> Some(value), None

def testOption(opt: Option[Any]) = opt match {
  case x: Option[Int] => "Int " + x.get
  case y: Option[String] => "String " + y.get
  case z => "Other " + z.get
}

testOption(Some(1))
testOption(Some("Hello"))
