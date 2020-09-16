// Implicit parameters

case class Tax(percentage: Double)

implicit val currentTax: Tax = Tax(10)
def calcPrice(unitPrice: Double, qty: Int)(implicit tax: Tax) = unitPrice * qty * (1 - tax.percentage / 100)

calcPrice(100.0, 2)

// !!!
// Не используйте стандартные и примитивные типы в качестве неявных параметров

