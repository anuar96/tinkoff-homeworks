// Option

val opt1: Option[Int] = Some(1)

opt1.get

val opt2: Option[Int] = None

// opt2.get

opt2.isEmpty

opt2.orElse(opt1)
opt2.getOrElse(1)

opt1.map(_ + 1)
opt1.flatMap(_ => Some(2))

for {
  x1 <- Some(1)
  x2 <- Some(2)
} yield x1 + x2

opt1 match {
  case Some(_) => "some"
  case None => "none"
}

opt2 match {
  case Some(_) => "some"
  case None => "none"
}