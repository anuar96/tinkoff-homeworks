// constructor match
// alternate match
// match with filter

(1, (1, (2, "2"))) match {
  case (1, (_, (2, "2"))) => "matched"
  case _ => "not matched"
}

// with alternate
(2, (1, (2, "2"))) match {
  case (1 | 2, (_, (2, "2"))) => "matched"
  case _ => "not matched"
}

// with filter
(3, "3") match {
  case (x , y) if x == y.toInt => "matched"
  case _ => "not matched"
}

// with variable binding
(1, (1, (2, "2"))) match {
  case (_, (_, inner@(_, _))) => "matched " + inner
  case _ => "not matched"
}