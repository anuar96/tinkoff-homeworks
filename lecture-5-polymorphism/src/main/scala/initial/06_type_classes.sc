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

// pet cafe with pet1 opt, pet opt, check average

// problem
// check what, doctor
// Хотелось бы расширить известные типы содержащие Pet для Check, но не менять иерархию


// <=

// type class
// check m[ ]
// implicit checks

// usage
// inspection result with before, after

// val doctor
// inspectCatsA,B with what
// use

