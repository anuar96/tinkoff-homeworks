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
    def map[B](f: A => B): F[B] = F.map(fa)(f)

    def flatMap[B](f: A => F[B]): F[B] = F.flatMap(fa)(f)

    def recover(pf: PartialFunction[Throwable, A]): F[A] = F.recover(fa)(pf)

    def recoverWith(pf: PartialFunction[Throwable, F[A]]): F[A] = F.recoverWith(fa)(pf)
  }

  implicit def recoverableForTry: Recoverable[Try] = new Recoverable[Try] {
    override def `new`[T](value: T): Try[T] = Try(value)

    override def map[A, B](fa: Try[A])(f: A => B): Try[B] = fa.map(f)

    override def flatMap[A, B](fa: Try[A])(f: A => Try[B]): Try[B] = fa.flatMap(f)

    override def recover[A](fa: Try[A])(pf: PartialFunction[Throwable, A]): Try[A] = fa.recover(pf)

    override def recoverWith[A](fa: Try[A])(pf: PartialFunction[Throwable, Try[A]]): Try[A] = fa.recoverWith(pf)

    override def raiseError[A](e: Throwable): Try[A] = Failure(e)
  }

  implicit def recoverableForFuture(implicit ec: ExecutionContext): Recoverable[Future] = new Recoverable[Future] {
    override def `new`[T](value: T): Future[T] = Future(value)

    override def map[A, B](fa: Future[A])(f: A => B): Future[B] = fa.map(f)

    override def flatMap[A, B](fa: Future[A])(f: A => Future[B]): Future[B] = fa.flatMap(f)

    override def recover[A](fa: Future[A])(pf: PartialFunction[Throwable, A]): Future[A] = fa.recover(pf)

    override def recoverWith[A](fa: Future[A])(pf: PartialFunction[Throwable, Future[A]]): Future[A] = fa.recoverWith(pf)

    override def raiseError[A](e: Throwable): Future[A] = Future.failed(e)
  }

  // Дополнительное задание повышенной сложности
  implicit def recoverableForCompletableFuture: Recoverable[CompletableFuture] = ??? // TODO
}