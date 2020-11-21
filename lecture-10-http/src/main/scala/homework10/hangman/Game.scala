package homework10.hangman

import java.time.Instant

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}

case class Game(id: Long,
                startedAt: Instant,
                state: State,
                status: GameStatus)

object Game {
  implicit val jsonDecoder: Decoder[Game] = deriveDecoder
  implicit val jsonEncoder: Encoder[Game] = deriveEncoder
}