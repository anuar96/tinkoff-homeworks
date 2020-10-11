// class type parameters
trait Pet

class Cat extends Pet {
  def meowSound: String = "meow"
}

// T
class PetShelter(val pets: Seq[Pet], val name: String)

// def shelterMeows(..): String =
// use method

// abstract type members
// trait PetShelterAbstract
// class CatShelterAbstract
// AT применяются в следующих случаях:
//  когда принципиально важно убрать TP из сигнатуры методов или типов.
// если TP имеют крайне сложный вид или их стало очень много и они делают код сложным для восприятия
//  что бы подчеркнуть, что AT по смыслу является частью типа в котором описан(отношение is-a)


// method type parameters
// def shelterMeows2


