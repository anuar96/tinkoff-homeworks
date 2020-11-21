package lecture10.circejson

import io.circe._, io.circe.generic.semiauto._
import io.circe.parser._, io.circe.syntax._

case class CoffeeOrder(coffeeType: String,
                       size: String,
                       name: String)

object CoffeeOrder {
  implicit val jsonDecoder: Decoder[CoffeeOrder] = deriveDecoder
  implicit val jsonEncoder: Encoder[CoffeeOrder] = deriveEncoder
}

object CoffeeOrderCirceInstances {
  ???
  ???
}

object Main extends App {

  val json = CoffeeOrder("Cappuccino", "Medium", "Donald").asJson.noSpaces // {"coffeeType":"Cappuccino","size":"Medium","name":"Donald"}
  decode[CoffeeOrder](json) // Right(CoffeeOrder(Cappuccino,Medium,Donald))
}