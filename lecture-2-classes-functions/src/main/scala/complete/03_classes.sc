import scala.util.Random
// Модификаторы приватности

class Card(private val number: String) {

 def maskedNumber: String = "******" + number.drop(6)

 override def equals(obj: Any) = obj match {
  case card: Card => this.number == card.number
 }


}

val card1 = new Card("123456714234432")
card1.maskedNumber

val card2 = new Card("123456714234431")
val card3 = new Card("123456714234431")

card1 == card2
card2 == card3


// private[this]

class CardV2(private val number: String) {

 private[this] val pin = Random.nextString(3)

 def maskedNumber: String = "******" + number.drop(6)

 override def equals(obj: Any) = obj match {
  case card: CardV2 => this.number == card.number
 }

 def checkPin(otherPin: String): Boolean = otherPin == pin
}
// private [package]