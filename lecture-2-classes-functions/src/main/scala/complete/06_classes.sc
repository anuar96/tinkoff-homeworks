// Наследование. Процесс описания нового класса на основе существующего. Наследуются поля и функциональность.

abstract class AbstractDeal {
  def commission: Double = 10
  val amount: Double
}

final class StockDeal(val amount: Double, val stock: String) extends AbstractDeal
final class CreditDeal(val amount: Double, val bank: String) extends AbstractDeal

new StockDeal(100, "AAPL").commission
new CreditDeal(100, "T-Bank").amount


// final

// super

class DoubleCommissionDeal(override val amount: Double) extends AbstractDeal {
  override def commission = super.commission * 2
}

new DoubleCommissionDeal(100).commission



