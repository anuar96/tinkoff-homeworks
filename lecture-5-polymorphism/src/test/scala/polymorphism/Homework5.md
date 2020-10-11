# Type classes

Есть два независимых типа кольца Ring и зарплаты Salary:
```scala
trait Ring[+T] extends Iterable[T]
case class Salary(employee: String, amount: Double)

class RingFromIterable[+T](iterable: Iterable[T]) extends Ring[T] {
  override def iterator: Iterator[T] = ???
}

object Ring {
  def apply[T](iterable: Iterable[T]): Ring[T] = new RingFromIterable[T](iterable)
}
```
Реализовать для них тайпкласс XN с алгеброй целочисленного умножения.
(Для Ring умножение означает последовательный повтор элемента заданное количество раз. Например: 1 1 2 2 3 3 1 1 ... из 1 2 3 1)
```scala
trait XN[M] {
  def x2(m: M): M // умножение в 2 раза
  def x3(m: M): M // умножение в 3 раза
  def x4(m: M): M // умножение в 4 раза
}
```
Синтаксис исходных типов должен быть так же расширен методами алгебры тайпкласса