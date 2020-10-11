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

// implicit def Printer

// object Check, apply, implicits, syntax
// use

// <=
