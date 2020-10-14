package lecture6.typeclass

import java.util.concurrent.CompletableFuture

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Try

trait FlatMap[F[_]] { // <-- Future[_], Try[_], Either[Throwable, _]
  def flatMap[A, B](fa: F[A])(f: A => F[B]): F[B]
}

object FlatMap {

  implicit def flatMapForFuture(implicit ec: ExecutionContext): FlatMap[Future] =
    new FlatMap[Future] {
      override def flatMap[A, B](fa: Future[A])
                                (f: A => Future[B]): Future[B] = {
        fa.flatMap(f)
      }
    }

  implicit val flatMapForTry: FlatMap[Try] = new FlatMap[Try] {
    override def flatMap[A, B](fa: Try[A])(f: A => Try[B]): Try[B] = fa.flatMap(f)
  }

  implicit val flatMapForJavaFuture: FlatMap[CompletableFuture] = new FlatMap[CompletableFuture] {
    override def flatMap[A, B](fa: CompletableFuture[A])(f: A => CompletableFuture[B]): CompletableFuture[B] = {
      fa.thenCompose { a: A => f(a) }
    }
  }

  implicit class FlatMapOps[F[_], A](fa: F[A])(implicit F: FlatMap[F]) {
    def flatMap[B](f: A => F[B]): F[B] = F.flatMap(fa)(f)
  }

}


object Main extends App {

  import FlatMap.FlatMapOps
  import scala.concurrent.ExecutionContext.Implicits.global

  type Transform[F[_], T] = T => F[T]

  def transform[F[_] : FlatMap, T](value: T)
                                  (initialTransform: Transform[F, T],
                                   transformations: Transform[F, T]*): F[T] = {
    transformations
      .foldLeft(initialTransform(value)) { (transformed, nextTransformation) =>
        transformed.flatMap(nextTransformation)
      }
  }


  // Future
  val result1 = transform(10)(int => Future(int * 100), int => Future(int / 0))
  //  println(Await.result(result1, Duration.Inf))

  // Try
  val result = transform(10)(int => Try(int * 100), int => Try(int / 0))
  println(result)

  // CompletableFuture
  val result3 = transform(10)(int => CompletableFuture.completedFuture(int * 100), int => CompletableFuture.completedFuture(int / 1))
  println(result3.get())


}