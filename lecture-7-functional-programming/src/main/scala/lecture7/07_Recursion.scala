package lecture7

import scala.annotation.tailrec

object Recursion extends App {

  case class Sign(plus: Int, zero: Int, munis: Int)

  def countSigns(seq: Seq[Int]): Sign = {
    var plus = 0
    var zero = 0
    var minus = 0

    seq
      .foreach { number =>
        number.sign match {
          case 0 => zero += 1
          case -1 => minus += 1
          case 1 => plus += 1
        }
      }

    Sign(plus, zero, minus)
  }

  def countSignsRec(seq: Seq[Int]): Sign = {

    @tailrec
    def countSignsRecImpl(seq: Seq[Int], plus: Int, minus: Int, zero: Int): Sign =
      seq match {
        case head +: tail =>
          head.sign match {
            case 0 => countSignsRecImpl(tail, plus, minus, zero + 1)
            case -1 => countSignsRecImpl(tail, plus, minus + 1, zero)
            case 1 => countSignsRecImpl(tail, plus + 1, minus, zero)
          }
        case Seq() => Sign(plus, zero, minus)
      }

    countSignsRecImpl(seq, 0, 0, 0)
  }

  println(countSigns(Seq(1, 1, 0, -123)))
}