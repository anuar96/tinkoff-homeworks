// Параметры по умолчанию

def calcPrice(unitPrice: Double, qty: Int, tax: Double = 20): Double = unitPrice * qty * (1 - tax / 100)

calcPrice(100, 2)