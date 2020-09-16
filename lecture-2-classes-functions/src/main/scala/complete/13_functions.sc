// Частично примененные и каррированные функции

def calcPrice(unitPrice: Double, qty: Int, tax: Double): Double = unitPrice * qty * (1 - tax / 100)

val tax18CalcPrice = calcPrice(_ ,_, 18)

tax18CalcPrice(100, 2)

def calcPriceCurried(tax: Double)(unitPrice: Double, qty: Int): Double = unitPrice * qty * (1 - tax / 100)

val tax20CalcPrice = calcPriceCurried(20) _

tax20CalcPrice(100, 2)