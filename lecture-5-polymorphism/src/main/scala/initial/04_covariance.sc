trait Pet

class Cat extends Pet {
  def meowSound: String = "meow"
}

class CatDog extends Cat {
  override def meowSound = "meoof"
  def burk: String = "woof"
}

class PetShelter[T <: Pet](val pets: Seq[T], val name: String)

// в прошлом примере не использовали T в имплементации
// можно обойтись наследованием параметризованного типа?
def shelterMeows(shelter: PetShelter[Cat]): String =
  s"""shelter: ${shelter.name}. Meows: ${shelter.pets.map(_.meowSound).mkString(", ")}"""
// Liskov substitution principle

// <=

// declaration site variance vs use site variance
// use on catdog shelter