package homework

class Economy
class UpgradedEconomy extends Economy
class Special1b extends UpgradedEconomy
class ExtendedEconomy extends Economy
class Business extends ExtendedEconomy
class Elite extends Business
class Platinum extends Business

class ServiceLevelAdvance[+T <: Economy] {
  def advance[R <: Economy]: ServiceLevelAdvance[R] = new ServiceLevelAdvance[R]
}

object Main extends App{
  val advance: ServiceLevelAdvance[UpgradedEconomy] = new ServiceLevelAdvance[UpgradedEconomy]
  val a: ServiceLevelAdvance[Economy] = advance.advance[Economy]
  val b: ServiceLevelAdvance[Special1b] = advance.advance[Special1b]

}