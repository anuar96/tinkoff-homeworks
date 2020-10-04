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
      case (prev, floor) =>
        if (floor.firstResident.age > olderThen && floor.firstResident.sex == Male
          || floor.secondResident.age > olderThen && floor.secondResident.sex == Male)
          prev + 1
        else prev
    }
  }
  def womanMaxAge(building: Building):Int = {
    protoFold(building, 0){
      case (prev, floor) =>
        floor match{
          case LivingFloor(Resident(age1, Female), Resident(age2, _), _) if age1 > age2 && age1 > prev => age1
          case LivingFloor(Resident(age1, _), Resident(age2, Female), _) if age2 > age1 && age2 > prev => age2
          case _ => prev
        }

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
