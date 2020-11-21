package homework10.hangman.api

import akka.http.scaladsl.model.StatusCodes.BadRequest
import akka.http.scaladsl.server.Directives.complete
import akka.http.scaladsl.server.ExceptionHandler
import homework10.hangman.HangmanException

object HangmanExceptionHandler {
  import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._

  val exceptionHandler: ExceptionHandler =
    ExceptionHandler {
      case e: HangmanException => complete(BadRequest, ExceptionResponse(e.getMessage))
    }
}