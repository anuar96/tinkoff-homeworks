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

//trait Check[M] {
//  def check[T](what: M[T], doctor: Doctor[T]): M[T]
//}

// <=

// type class
trait Check[M[_]] {
  def check[T <: Pet](what: M[T], doctor: Doctor[T]): M[T]
}

implicit val petShelterCheck: Check[PetShelter] = new Check[PetShelter] {
  override def check[T <: Pet](what: PetShelter[T], doctor: Doctor[T]) = {
    new PetShelter[T](
      what.pets.filter(doctor.isHealthy),
      what.name
    )
  }
}

implicit val petCafeCheck: Check[PetCafe] = new Check[PetCafe] {
  override def check[T <: Pet](what: PetCafe[T], doctor: Doctor[T]) = {
    PetCafe(
      what.pet1.filter(doctor.isHealthy),
      what.pet2.filter(doctor.isHealthy),
      what.averageCheck
    )
  }
}

case class InspectionResult[M[_], T](before:M[T], after: M[T])

val doctor = new Doctor[Cat] {
  override def isHealthy(patient: Cat) = patient.meowSound == "meow"
}

def inspectCatsA[M[_]](what: M[Cat])(implicit ev: Check[M]): InspectionResult[M, Cat] = {
  val checked: M[Cat] = ev.check(what, doctor)
  InspectionResult(
    what,
    checked
  )
}
def inspectCatsB[M[_]:Check](what: M[Cat]): InspectionResult[M, Cat] = {
  val checked: M[Cat] = implicitly[Check[M]].check(what, doctor)
  InspectionResult(
    what,
    checked
  )
}

inspectCatsA(new PetShelter(Seq(new Cat),"shelter"))
inspectCatsB(new PetCafe[Cat](Some(new Cat), Some(new Cat), 1.4))

// <=
