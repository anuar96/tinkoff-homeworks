import scala.util.Random

trait Pet

class Cat extends Pet {
  def meowSound: String = "meow"
}

class CatDog extends Cat {
  override def meowSound = "meoof"
  def burk: String = "woof"
}

trait PetDoctor[T <: Pet] {
  def isHealthy(pet: T): Boolean
}

class CheaterDoctor[T <: Pet] extends PetDoctor[T] {
  override def isHealthy(pet: T): Boolean = Random.nextBoolean()
}
// Является ли CheaterDoctor[CatDog] потомком CheaterDoctor[Cat] ?

def giveBirthToCat(doctor: PetDoctor[Pet]): Option[Cat] = {
  val cat = new Cat
  if (doctor.isHealthy(cat)) Some(cat) else None
}

val catDogDoctor = new CheaterDoctor[CatDog]{
  override def isHealthy(pet: CatDog) = pet.burk == "meow"
}
// ups
//giveBirthToCat(catDogDoctor)

trait ContrvariantPetDoctor[-T <: Pet] {
  def isHealthy(pet: T): Boolean
}

class ContrvariantCheaterDoctor[-T <: Pet] extends ContrvariantPetDoctor[T] {
  override def isHealthy(pet: T): Boolean = Random.nextBoolean()
}

val catDoctor = new ContrvariantPetDoctor[Cat] {
  override def isHealthy(pet: Cat): Boolean = pet.meowSound == "meow"
}

//  CheaterDoctor[CatDog] является предком CheaterDoctor[Cat]

def giveBirthToCatDog2(doctor: ContrvariantPetDoctor[CatDog]): Option[CatDog] = {
  val cat = new CatDog
  Some(cat).filter(doctor.isHealthy)
}

val catDogDoctor2 = new ContrvariantCheaterDoctor[CatDog]{
  override def isHealthy(pet: CatDog) = pet.burk != "meow"
}

giveBirthToCatDog2(catDogDoctor2)
giveBirthToCatDog2(catDoctor)

