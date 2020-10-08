trait Pet

class Cat extends Pet {
  def meowSound: String = "meow"
}

class CatDog extends Cat {
  override def meowSound = "meoof"
  def burk: String = "woof"
}

// declaration site variance vs use site variance
class PetShelter[+T <: Pet](val pets: Seq[T], val name: String)

// Liskov substitution principle

// <=

def shelterMeows(shelter: PetShelter[Cat]): String =
  s"""shelter: ${shelter.name}. Meows: ${shelter.pets.map(_.meowSound).mkString(", ")}"""

val catDogShelter = new PetShelter[CatDog](Seq(new CatDog), "cat dog shelter")

shelterMeows(catDogShelter)


class  Test[T]

