package homework10.hangman.api

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import homework10.hangman.logic.GameService

class AdminApi(gameService: GameService) {

  import akka.http.scaladsl.server.Directives._
  import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport.marshaller

  val route: Route = complete(StatusCodes.NotImplemented) // TODO
}
