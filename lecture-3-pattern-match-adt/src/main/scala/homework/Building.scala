package homework

import scala.util.{Failure, Success}

object Building{
  @scala.annotation.tailrec
  def protoFold(building: Building, acc0: Int)(f: (Int, LivingFloor) => Int): Int = {
    building.floor match{
      case livingFloor: LivingFloor => protoFold(building.copy(floor = livingFloor.nextFloor) ,f(acc0, livingFloor))(f)
      case Attic => acc0
    }
  }
  def countOldManFloors(building: Building, olderThen: Int): Int = {
    protoFold(building, 0){
      case (prev, LivingFloor(Resident(age, Male), _, _)) if age > olderThen => prev + 1
      case (prev, LivingFloor(_, Resident(age, Male), _)) if age > olderThen => prev + 1
      case (prev, _) => prev
    }
  }
  def womanMaxAge(building: Building):Int = {
    protoFold(building, 0){
      case (prev, LivingFloor(Resident(age1, Female), Resident(age2, Female), _))  => age1 max age2 max prev
      case (prev, LivingFloor(Resident(age1, Female), _, _)) => age1 max prev
      case (prev, LivingFloor(_, Resident(age2, Female), _)) => age2 max prev
      case (prev, _) => prev
    }
  }
}

case class Building(address: String, floor: Floor)

sealed trait Floor

sealed trait Sex

case object Male extends Sex

case object Female extends Sex

case class Resident(age: Int, sex: Sex)

case class LivingFloor(firstResident: Resident, secondResident: Resident, nextFloor: Floor) extends Floor

case object Attic extends Floor
