// Функция как значение

val sum: (Int, Int) => Int = (x, y) => x + y

sum(1, 2)

// Без параметров
val sayHello: Unit => Unit = _ => println("Hello")

sayHello()

// Присвоение функции определенной через def

def someFunction(): Int = 1

val someFunctionAsVal: () => Int = someFunction _
