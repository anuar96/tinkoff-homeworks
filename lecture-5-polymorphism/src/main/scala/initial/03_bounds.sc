trait Pet

class Cat extends Pet {
  def meowSound: String = "meow"
}

class PetShelter[T](val pets: Seq[T], val name: String)

def shelterMeows(shelter: PetShelter[Cat]): String =
  s"""shelter: ${shelter.name}. Meows: ${shelter.pets.map(_.meowSound).mkString(", ")}"""

// class Universe, universe pet shelter

// class type parameter upper bound
// class BoundedPetShelter
// use

// cat dog, burk
// val catDogShelter
// shelterMeows(catDogShelter), ups
// method type parameter upper bound, in place parameter bound
// use

// ShrodingerCat <hidden>
// shelterMeows3, method type parameter lower and upper bound
// use

// inference в общем родителе
// A, B, C, D
trait A
class B extends A
class C extends A
class D extends C
//def f[T >: C](e: T): T = ???
//val f1: C = f(new D)
//val f2: A = f(new B)

//  <=