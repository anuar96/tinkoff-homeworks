import scala.language.implicitConversions
import scala.util.Random

trait Pet

class Cat extends Pet {
  def meowSound: String = "meow"
}

trait Doctor[-T <: Pet] {
  def isHealthy(pet: T): Boolean
}

class CheaterDoctor[T <: Pet] extends Doctor[T] {
  override def isHealthy(pet: T): Boolean = Random.nextBoolean()
}

class PetShelter[+T](val pets: Seq[T], val name: String)

case class PetCafe[+T](val pet1: Option[T],
                       val pet2: Option[T],
                       val averageCheck: Double)

val doctor = new Doctor[Cat] {
  override def isHealthy(patient: Cat) = patient.meowSound == "meow"
}


trait Check[M[_]] {
  def check[T <: Pet](what: M[T], doctor: Doctor[T]): M[T]
}


trait Printer {
  def printMe(): Unit
}

implicit def shelterPrinter[T](shelter: PetShelter[T]): Printer = new Printer {
  override def printMe(): Unit = println(s"print me: ${shelter.name}" )
}

val shelter = new PetShelter[Cat](Seq.empty, "Test")
shelter.printMe()


object Check {

  def apply[M[_]](implicit instance: Check[M]): Check[M] = instance

  implicit def checkSyntax[M[_] : Check, T <: Pet](m: M[T]): CheckOps[M, T] = new CheckOps[M, T](m)

  implicit val petShelterInspection: Check[PetShelter] = new Check[PetShelter] {
    override def check[T <: Pet](what: PetShelter[T], doctor: Doctor[T]) = {
      new PetShelter[T](
        what.pets.filter(doctor.isHealthy),
        what.name
      )
    }
  }

  implicit val petCafeInspection: Check[PetCafe] = new Check[PetCafe] {
    override def check[T <: Pet](what: PetCafe[T], doctor: Doctor[T]) = {
      PetCafe(
        what.pet1.filter(doctor.isHealthy),
        what.pet2.filter(doctor.isHealthy),
        what.averageCheck
      )
    }
  }

  final class CheckOps[M[_] : Check, T <: Pet](m: M[T]) {
    def check(doctor: Doctor[T]): M[T] = Check[M].check(m, doctor)
  }

}

import Check._
val shelter = new PetShelter(Seq(new Cat), "shelter")
shelter.check(doctor)

