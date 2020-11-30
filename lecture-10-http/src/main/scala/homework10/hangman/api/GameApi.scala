package homework10.hangman.api

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.unmarshalling.Unmarshaller
import homework10.hangman.logic.PlayerGameService

class GameApi(gameService: PlayerGameService) {

  import akka.http.scaladsl.server.Directives._
  import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport.marshaller

  val route: Route = complete(StatusCodes.NotImplemented) // TODO

  implicit val charUnmarshaller: Unmarshaller[String, Char] =
    Unmarshaller.strict[String, Char] { string =>
      if (string.length == 1) string.head
      else throw new IllegalArgumentException("Got string or nothing but char expected")
    }
}

