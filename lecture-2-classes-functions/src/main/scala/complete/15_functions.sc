// Функция как Класс

val f1: Int => Int = _ + 1

val f2 = new Function1[Int, Int] {
  override def apply(v1: Int) = v1 + 1
}


f1(1)

f2(1)


// Композиция функций

f2.compose((_: Int) * 2)(1)

f2.andThen(_ * 2)(1)

