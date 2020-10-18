package homework

trait Language{
  def greeting: String
}

object Russian extends Language{
  def greeting: String = "Привет!"
}

object English extends Language{
  def greeting: String = "Hello!"
}

trait Greeting {
  val language: Language
  def text: String = language.greeting
}


class Greeter {
  def greet(greetings: Greeting): Unit = println(greetings.text)
}




