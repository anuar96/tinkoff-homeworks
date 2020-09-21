case class Wealth(money: Int, force: Int)

trait GreatHouse {
  val name: String
  val wealth: Wealth

  override def toString: String = s"$name: $wealth"
}

trait MakeWildFire {
  self: GreatHouse =>

  def makeWildFire(greatHouse: GreatHouse) = wealth.copy(force = wealth.force + 100)
}

trait BorrowMoney {
  self: GreatHouse =>

  def borrowMoney(greatHouse: GreatHouse) = wealth.copy(money = wealth.money + 100)
}

trait CallDragon {
  self: GreatHouse =>

  def callDragon(greatHouse: GreatHouse) = wealth.copy(force = wealth.force * 2)
}

case class Lannisters(wealth: Wealth) extends GreatHouse with MakeWildFire with BorrowMoney {
  override val name = "Lannister"
}

case class Targaryen(wealth: Wealth) extends GreatHouse with CallDragon with BorrowMoney {
  override val name = "Targaryen"
}

class GameOfThrones(val lannisters: Lannisters, val targaryen: Targaryen, val turn: Int) {

  def nextTurn(lannisterStrategy: Lannisters => Wealth)(targaryenStrategy: Targaryen => Wealth): GameOfThrones = {
    new GameOfThrones(Lannisters(lannisterStrategy(lannisters)), Targaryen(targaryenStrategy(targaryen)), turn + 1)
  }

  override def toString: String = s"Turn number $turn\n$lannisters\n$targaryen\n"
}

val lannistersInit = Lannisters(Wealth(1000, 100))
val targaryenInit = Targaryen(Wealth(100, 10))
val startGame = new GameOfThrones(lannistersInit, targaryenInit, 1)
println(startGame)
val secondTurn = startGame.nextTurn(startGame.lannisters.borrowMoney)(startGame.targaryen.callDragon)
println(secondTurn)
val thirdTurn = secondTurn.nextTurn(secondTurn.lannisters.makeWildFire)(secondTurn.targaryen.callDragon)
println(thirdTurn)