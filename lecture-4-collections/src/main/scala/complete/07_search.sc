// search

// find
Seq(1, 2, 3).find(_ == 2)
Seq(1, 2, 3).find(_ == 10)

// contains
Seq(1, 2, 3).contains(2)
Seq(1, 2, 3).contains(20)
// contains class
class A
Seq(new A, new A).contains(new A)

// exists >, forAll
Seq(1, 2, 3).exists(_ > 2)
Seq(1, 2, 3).forall(_ > 2)
