trait Pet

trait PetShelter {
  def pets: Seq[Pet]
  def name: String
}

class Cat extends Pet {
  def meowSound: String = "meow"
}

class Tarantula extends Pet

def shelterMeows(shelter: PetShelter) = ???
// один из вариантов смотреть instance of, но он грязный, потому что runtime

//  <=