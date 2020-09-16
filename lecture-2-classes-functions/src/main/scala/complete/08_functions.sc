// f: (Int, Int) => Int . Определяются в классе
def sum(x: Int, y: Int): Int = x + y

sum(1 , 3)

// no parameters
def sayHello(): Unit = println("Hello")

sayHello()

// Без списка параметров
def getNumber: Int = 5

getNumber

// Именованные параметры
sum(x= 1 ,y = 2)

// Хорошим тоном считается указание имени для Boolean и Option параметров
def offerSalary(isGenius: Boolean): Int = if(isGenius)100000 else 100

offerSalary(isGenius = true)

// varargs

def sayHello(names: String*): Unit = {
  names.foreach(name => println(s"hello $name"))
}

sayHello("Alex", "Bob")

// nested function

def sayHello2(names: String*): Unit = {
  def sayHello(name: String) = println(s"hello $name")
  names.foreach(sayHello)
}
