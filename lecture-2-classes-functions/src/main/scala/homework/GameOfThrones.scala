package homework

import scala.util.Random

case class Wealth(money: Double, armyPower: Long)

trait GreatHouse{
  val wealth: Wealth
}

trait Ability{
  val random = new Random()
}

trait CallDragon extends Ability {
  this: GreatHouse =>

  def callDragon: Wealth = wealth.copy(armyPower = wealth.armyPower * 2)
}

trait BorrowMoney extends Ability {
  this: GreatHouse =>

  def borrowMoney: Wealth = wealth.copy(money = wealth.money * random.nextInt(500))
}

trait MakeWildFire extends Ability{
  this: GreatHouse =>

  def makeWildFire: Wealth = wealth.copy(armyPower = wealth.armyPower * random.nextInt(1000))
}

case class Lannisters(wealth: Wealth) extends GreatHouse with BorrowMoney with MakeWildFire

case class Targaryen(wealth: Wealth) extends GreatHouse with CallDragon with MakeWildFire

case class GameOfThrones(lannisters: Lannisters, targaryen: Targaryen, turn: Long) {
  def nextTurn(lannisterMove: Lannisters => Wealth)(targeryenMove: Targaryen => Wealth): GameOfThrones = {
    GameOfThrones(Lannisters(lannisterMove(lannisters)), Targaryen(targeryenMove(targaryen)), turn + 1)
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
  val targeryen = Targaryen(Wealth(100, 200))

  val game = GameOfThrones(lannisters, targeryen, 1)
    .nextTurn(_.borrowMoney)(_.callDragon)
    .nextTurn(_.makeWildFire)(_.callDragon)

  println(game)
}
