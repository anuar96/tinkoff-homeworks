package homework10.hangman

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto._

final case class State(name: String, guesses: Set[Char], word: String) {
  def failures: Int = (guesses -- word.toSet).size

  def playerLost: Boolean = failures >= 10

  def playerWon: Boolean = (word.toSet -- guesses).isEmpty

  def addChar(char: Char): State = copy(guesses = guesses + char)
}

object State {
  implicit val jsonDecoder: Decoder[State] = deriveDecoder
  implicit val jsonEncoder: Encoder[State] = deriveEncoder
}