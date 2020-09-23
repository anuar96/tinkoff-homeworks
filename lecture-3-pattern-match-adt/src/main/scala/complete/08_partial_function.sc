// Частично определенная функция
// Partial Function
// Функции определены не на всей области значений параметра

case class Payment(amount: Double)

val paymentFactory1 = new PartialFunction[Double, Payment] {

  override def isDefinedAt(x: Double) = x > 0

  override def apply(v1: Double) = Payment(v1)
}

val paymentFactory2: PartialFunction[Double, Payment] = {
  case x if x > 0 => Payment(x)
}

paymentFactory1(-2)
//paymentFactory2(-2)

// Пример использования частично определенной функции в коллекциях

Vector(1.0, 2.0, -3.0).collect(paymentFactory1)

Vector(1.0, 2.0, -3.0).collect(paymentFactory2)

Vector(1, 2, -3).collect {
  case x if x > 0 => Payment(x)
}

// <=


