package lecture7

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

object Program_02 extends App {
  val compressor = new Compressor_02

  val input = Seq(1, 2, 2, 3, 3, 3, 4)
  val output = Seq((1, 1), (2, 2), (3, 3), (4, 1))
  assert(compressor.compress(input) == output)
}

class Compressor_02 {
  def compress(numbers: Seq[Int]): Seq[(Int, Int)] = { // <---- immutable
    val cache: mutable.Map[Int, Int] = mutable.HashMap[Int, Int]() // <--- move from global to local
    var prev = 0
    var index = 0
    var result = ArrayBuffer[(Int, Int)]()

    while (index < numbers.size) {
      val current = numbers(index)
      if (index == 0) {
        prev = current
        cache.put(current, 1)
      } else {
        if (prev == current) {
          val counter = cache(current) + 1
          cache.put(current, counter)
        } else {
          result += (prev -> cache(prev))
          cache.remove(prev)
          cache.put(current, 1)
        }
        prev = current
      }
      index += 1
    }

    if (cache.nonEmpty) result += cache.head

    result.toSeq
  }
}