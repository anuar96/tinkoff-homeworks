package lecture6.future

import java.io.Closeable
import java.util.concurrent.{Executors, TimeUnit}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent._
import scala.concurrent.duration._
import scala.io.{BufferedSource, Source}

case class Bucket(min: Int, max: Int, count: Int)


case class Stats(lineCount: Int,
                 minLineLength: Int,
                 averageLineLength: Int,
                 maxLineLength: Int,
                 buckets: Seq[Bucket])


object Practice extends App {
  private val bucketSize = 10
  private val defaultFileName = "in.txt"

  private def getSource(fileName: String): BufferedSource =
    Source.fromResource(fileName)
  private def read(in: BufferedSource): Future[Iterator[String]] =
    Future(blocking(in.getLines()))

  private def printStats(stats: Stats): Unit = {
    import stats._
    println(
      s"""
         | Total line count: $lineCount
         | min: $minLineLength
         | avg: $averageLineLength
         | max: $maxLineLength
         |
         | buckets:
         |${
        buckets.map { b =>
          import b._
          s"   - $min-$max: $count"
        }.mkString("\n")
      }""".stripMargin)
  }

  /*
    Методы для разминки
   */

  def asyncWithResource[R <: Closeable, T](resource: R)(block: R => Future[T]): Future[T] = {
    block(resource)
      .andThen {
        case _ => resource.close()
      }
  }

  def asyncCountLines: Future[Int] =
    asyncWithResource(getSource(defaultFileName))((source: BufferedSource) =>
      for {
        lines <- read(source)
      } yield lines.length
    )

  def asyncLineLengths: Future[Seq[(String, Int)]] =
    asyncWithResource(getSource(defaultFileName)) { source =>
      for {
        lines <- read(source)
      } yield lines.map(line => line -> line.length).toSeq
    }

  def asyncTotalLength: Future[Int] =
    asyncLineLengths.map { lineToLength =>
      lineToLength.map { case (_, size) => size }.sum
    }

  def countShorterThan(maxLength: Int): Future[Int] =
    ???


  /*
    Sleep sort
    https://www.quora.com/What-is-sleep-sort
   */

  val scheduller = Executors.newSingleThreadScheduledExecutor()

  def printWithDelay(delay: FiniteDuration, s: String): Future[Unit] = {
    val promise = Promise[Unit]()

    scheduller.schedule(
      new Runnable {
        override def run(): Unit = {
          println(s)
          promise.success(())
        }
      },
      delay.toMillis,
      TimeUnit.MILLISECONDS
    )

    promise.future
  }

  def sleepSort: Future[Unit] =
    ???

  /*
    Calculate file statistics
   */

  def splitToBuckets(linesWithLengths: Seq[(String, Int)]): Future[Seq[Bucket]] =
    ???

  def calculateStats: Future[Stats] =
    ???


  val result = Await.result(asyncCountLines, 10.seconds)
  println(result)


}
