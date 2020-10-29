package lecture7

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future}
import scala.concurrent.duration.Duration

// Порефачим ??? :D
object Composability_05 extends App {

  def validateNumber(i: Int): Future[Int] = {
    if (i == 2) throw new IllegalArgumentException("Number is 2 :( ")
    Future.successful(i)
  }

  def program: Future[Int] = {
    (for {
      number <- Future.successful(2)
      _ <- validateNumber(number)
    } yield number).recover { case _ => 42 }
  }

  println(Await.result(program, Duration.Inf))
}
