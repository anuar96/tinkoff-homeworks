trait Pet

class Cat extends Pet {
  def meowSound: String = "meow"
}

class CatDog extends Cat {
  override def meowSound = "meoof"
  def burk: String = "woof"
}
// PetDoctor isHealthy
// random CheaterDoctor
// Является ли CheaterDoctor[CatDog] потомком CheaterDoctor[Cat] ?

// giveBirthToCat Option[Cat]
// является по имплементации

// override Cheater
// use, ups

//  ContrvariantPetDoctor
// giveBirthToCatDog2 with CatDog
//use

// <=
