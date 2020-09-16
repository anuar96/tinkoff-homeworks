// Traits. Нельзя создать экземпляр

trait Fund {
  val price: Double
  def doublePrice: Double = price * 2
}

trait HasDirector {
  def directorName: String
  def helloDirector: String = s"Hello director $directorName"
}

class MyFund extends Fund with HasDirector {
  override val price = 100
  override def directorName = "Alexander"
}

new MyFund().doublePrice
new MyFund().helloDirector

// self type
// Возможны циклические ссылки
// TriplePrice

trait TriplePrice {
  this: Fund =>
  def triplePrice: Double = price * 3
}

val triplePricedFund = new MyFund with TriplePrice
triplePricedFund.triplePrice


// <-