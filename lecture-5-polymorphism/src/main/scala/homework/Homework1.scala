package homework

trait Language

object Russian extends Language

object English extends Language

trait Greeting[T <: Language] {
  def text: String
}

class Greeter[T <: Language] {
  def greet(greetings: Greeting[T]): Unit = println(greetings.text)
}

object Main1 extends App{
  new Greeter[Russian.type].greet(new Greeting[Russian.type]{
    def text = "Привет!"
  })
  new Greeter[Russian.type].greet(
    new Greeting[Russian.type]{
      def text = "Добрый день!"
    }
  )
  new Greeter[English.type].greet{
    new Greeting[English.type]{
      def text = "Hello!"
    }
  }
/*  new Greeter[English.type].greet{
    new Greeting[Russian.type]{
      def text = "Hello1"
    }
  }*/ // не компилится, потому что нельзя приветствовать на другом языке
}


