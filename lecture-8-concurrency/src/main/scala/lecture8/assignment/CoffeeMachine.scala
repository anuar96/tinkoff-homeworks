package lecture8.assignment

class CoffeeMachine {
  @volatile private var turnedOn = true

  def turnOff(): Unit = {
    turnedOn = false
    ()
  }

  def run(): Unit = {
    println("DOING COFFEEE...")
    while (turnedOn) {}
    println("STOPPING...")
  }

}
