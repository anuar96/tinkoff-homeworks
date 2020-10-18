package lecture6.typeclass

import java.util.concurrent.CompletableFuture

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success, Try}

trait Recoverable[F[_]] {
  def `new`[T](value: T): F[T]

  def map[A, B](fa: F[A])(f: A => B): F[B]

  def flatMap[A, B](fa: F[A])(f: A => F[B]): F[B]

  def recover[A](fa: F[A])(pf: PartialFunction[Throwable, A]): F[A]

  def recoverWith[A](fa: F[A])(pf: PartialFunction[Throwable, F[A]]): F[A]

  def raiseError[A](e: Throwable): F[A]
}

object Recoverable {

  implicit class RecoverableOps[F[_], A](fa: F[A])(implicit F: Recoverable[F]) {
    def map[B](f: A => B): F[B] = ???

    def flatMap[B](f: A => F[B]): F[B] = ???

    def recover(pf: PartialFunction[Throwable, A]): F[A] = ???

    def recoverWith(pf: PartialFunction[Throwable, F[A]]): F[A] = ???
  }

  implicit def recoverableForTry: Recoverable[Try] = ??? // TODO

  implicit def recoverableForFuture(implicit ec: ExecutionContext): Recoverable[Future] = ??? // TODO

  // Дополнительное задание повышенной сложности
  implicit def recoverableForCompletableFuture: Recoverable[CompletableFuture] = ??? // TODO
}