// tuple match

(1, "2") match {
  case (1, "2") => "matched"
  case _ => "not matched"
}
// tuple with wildcard
(3, "2") match {
  case (_, "2") => "matched"
  case _ => "not matched"
}

