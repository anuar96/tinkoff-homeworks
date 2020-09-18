package homework

import scala.util.Random


case class Wealth(money: Double, armyPower: Long)

trait GreatHouse{
  val wealth: Wealth
}

trait Ability{
  val random = new Random()

  def abs(n: Long): Long ={
    if (n < 0) -n else n
  }
}

trait CallDragon extends Ability {
  this: GreatHouse =>

  def callDragon: Wealth = wealth.copy(armyPower = wealth.armyPower * 2)

}

trait BorrowMoney extends Ability {
  this: GreatHouse =>

  def borrowMoney: Wealth = wealth.copy(money = wealth.money * random.nextInt(500).abs)

}

trait MakeWildFire extends Ability{
  this: GreatHouse =>

  def makeWildFire: Wealth = wealth.copy(armyPower = wealth.armyPower * random.nextInt(1000).abs)
}

case class Lannisters(wealth: Wealth) extends GreatHouse with BorrowMoney with MakeWildFire

case class Targaryen(wealth: Wealth) extends GreatHouse with CallDragon with MakeWildFire

case class GameOfThrones(lannisters: Lannisters, targaryen: Targaryen, turn: Long) {
  def nextTurn(lannistterMove: () => Wealth)(targeryenMove: () => Wealth): GameOfThrones = {
    GameOfThrones(Lannisters(lannistterMove()), Targaryen(targeryenMove()), turn + 1)
  }

  def whoIsWinning(): GreatHouse = {
    if (lannisters.wealth.armyPower + lannisters.wealth.money > targaryen.wealth.money + targaryen.wealth.armyPower)
      lannisters
    else
      targaryen
  }
}

object Main extends App{
  val lannisters = Lannisters(Wealth(100, 200))
  val targeryen = Targaryen(Wealth(101, 199))

  println(GameOfThrones(lannisters, targeryen, 1)
    .nextTurn(() => lannisters.borrowMoney)(() => targeryen.callDragon)
    .nextTurn(() => lannisters.makeWildFire)(() => targeryen.makeWildFire)
    .whoIsWinning()
  )
}
