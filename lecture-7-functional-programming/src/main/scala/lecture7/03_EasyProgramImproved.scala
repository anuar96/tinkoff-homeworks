package lecture7

import scala.collection.immutable.{AbstractSeq, LinearSeq}
import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

object Program_03 extends App {
  val compressor = new Compressor_03

  val input = Seq(1, 2, 2, 3, 3, 3, 4)
  val output = Seq((1, 1), (2, 2), (3, 3), (4, 1))
  println(compressor.compress(input))
  assert(compressor.compress(input) == output)
}

import CompressorCache._

class Compressor_03 {
  def compress(numbers: Seq[Int]): Seq[(Int, Int)] = { // <---- immutable

    numbers
      .foldLeft(ArrayBuffer[(Int, Int)]()) { (acc, elem) =>
        acc.lastOption match {
          case Some(last -> count) if last == elem =>
            acc(acc.size - 1) = (elem, count + 1)
            acc
          case Some(last -> count) if last != elem =>
            acc.addOne(elem, 1)
            acc
          case None =>
            acc.addOne(elem, 1)
            acc
        }
      }
      .toSeq
  }
}