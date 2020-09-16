// Функция как параметр
// Higher-order function
// closure

case class Order(qty: Int, price: Double)

def makeOrder(qty: Int, discounter: Double => Double): Order = {
  val ItemPrice = 100
  val price = qty * ItemPrice
  Order(qty, price - discounter(price))
}

makeOrder(2, x => x * 0.1)
makeOrder(2, _ * 0.1)

def discount(price: Double): Double = price * 0.1
makeOrder(2, discount)

