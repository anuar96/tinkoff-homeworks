// grouped, sorting, partition, zip

List(1, 2, 3, 4, 5).grouped(2).toList
List(1, 5, 2).sorted
(1 to 10).toList.partition(_ % 2 == 0)
List(1, 2, 3) zip List("a", "b")