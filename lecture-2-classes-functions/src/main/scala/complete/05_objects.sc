// Singleton

object Singleton {
 def sayHello: String = "hello"
}

Singleton.sayHello

// Companion objects

class Card(private val number: String)

object Card {

 def apply(): Card = TestCard
 def forceShowCardNumber(card: Card): String = card.number

 private lazy val TestCard = new Card("12321432131")

}

Card()
