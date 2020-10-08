// class type parameters
trait Pet

class Cat extends Pet {
  def meowSound: String = "meow"
}

class PetShelter[T](val pets: Seq[T], val name: String)

def shelterMeows(shelter: PetShelter[Cat]): String =
  s"""shelter: ${shelter.name}. Meows: ${shelter.pets.map(_.meowSound).mkString(", ")}"""


val shelter = new PetShelter[Cat](Seq(new Cat, new Cat), "my shelter")

shelterMeows(shelter)


// abstract type members
trait PetShelterAbstract {
  type T
  val pets: Seq[T]
  val name: String
}

class CatShelterAbstract(val pets: Seq[Cat],
                            val name: String) extends PetShelterAbstract {
  type T = Cat
}

// AT применяются в следующих случаях:
//  когда принципиально важно убрать TP из сигнатуры методов или типов.
// если TP имеют крайне сложный вид или их стало очень много и они делают код сложным для восприятия
//  что бы подчеркнуть, что AT по смыслу является частью типа в котором описан(отношение is-a)


// method type parameters
def shelterMeows2[T](shelter: PetShelter[T]): String = ???


