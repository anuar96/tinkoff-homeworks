trait Pet

class Cat extends Pet {
  def meowSound: String = "meow"
}

class PetShelter[T](val pets: Seq[T], val name: String)

//////////

class Universe

val strangeShelter = new PetShelter[Universe](Seq(new Universe), "my shelter")

// class type parameter upper bound
class BoundedPetShelter[T <: Pet](val pets: Seq[T], val name: String)

//val strangeShelter = new BoundedPetShelter[Universe](Seq(new Universe), "my shelter")

class CatDog extends Cat {
  override def meowSound = "meoof"
  def burk: String = "woof"
}

val catDogShelter = new BoundedPetShelter[CatDog](Seq(new CatDog), "cat dog shelter")

def shelterMeows(shelter: BoundedPetShelter[Cat]): String =
  s"""shelter: ${shelter.name}. Meows: ${shelter.pets.map(_.meowSound).mkString(", ")}"""

//shelterMeows(catDogShelter)

// method type parameter upper bound
def shelterMeows2a[T <: Cat](shelter: BoundedPetShelter[T]): String =
  s"""shelter: ${shelter.name}. Meows: ${shelter.pets.map(_.meowSound).mkString(", ")}"""
def shelterMeows2b(shelter: BoundedPetShelter[_ <: Cat]): String =
  s"""shelter: ${shelter.name}. Meows: ${shelter.pets.map(_.meowSound).mkString(", ")}"""

shelterMeows2a(catDogShelter)
shelterMeows2b(catDogShelter)


class ShrodingerCat extends CatDog {
  override def meowSound = "<hidden>"
  override def burk = "<hidden>"
}

// method type parameter lower and upper bound
def shelterMeows3[T >: CatDog <: Cat](shelter: BoundedPetShelter[T]): String =
  s"""shelter: ${shelter.name}. Meows: ${shelter.pets.map(_.meowSound).mkString(", ")}"""

shelterMeows3(catDogShelter)

// inference
trait A
class B extends A
class C extends A
class D extends C
//def f[T >: C](e: T): T = ???
//val f1: C = f(new D)
//val f2: A = f(new B)


///////


