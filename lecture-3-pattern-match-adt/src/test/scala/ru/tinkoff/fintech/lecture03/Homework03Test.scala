package ru.tinkoff.fintech.lecture03

import homework._
import org.scalatest.funsuite.AnyFunSuite

class Homework03Test extends AnyFunSuite {
  val tree: Node = Node(32,RedLeaf, Node(2,Node(150,GreenLeaf ,GreenLeaf),YellowLeaf))
  test("countYellowAndRedValues") {
    assert(Tree.countYellowAndRedValues(tree) == 2)
  }

  test("maxValue") {
    assert(Tree.maxValue(tree).contains(150))
  }

  val building: Building = Building("moscow leninskie gory street 1",
    LivingFloor(Resident(12, Female), Resident(40, Female),
      LivingFloor(Resident(14, Male), Resident(32, Male), LivingFloor(Resident(22, Female), Resident(23, Male), Attic))
    ))

  test("protoFold") {
    val result = Building.protoFold(building, 0){case (int, floor) => int}
    assert(result == 0)
  }

  test("countOldManFloors") {
    val result = Building.countOldManFloors(building,31)
    assert(result == 1)
  }

  test("womanMaxAge") {
    val result = Building.womanMaxAge(building)
    assert(result == 40)
  }
}
