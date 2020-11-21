package homework10.hangman.api

import java.time.Instant

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.testkit.ScalatestRouteTest
import homework10.hangman
import homework10.hangman.{Game, GameStatus, State}
import homework10.hangman.logic.GameService
import org.scalamock.scalatest.MockFactory
import org.scalatest.funspec.AnyFunSpec

import scala.concurrent.Future

class AdminApiSpec extends AnyFunSpec with ScalatestRouteTest with MockFactory {

  import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._

  describe("GET /admin/game/1") {
    it("возвращает текущее состояние игры с немаскированным словом") {
      (mockGameService.find _)
        .expects(1)
        .returns(Future.successful(Some(sampleGame)))

      Get("/admin/game/1") ~> route ~> check {
        assert(status == StatusCodes.OK)
        assert(responseAs[Option[Game]].contains(sampleGame))
      }
    }

    it("возвращает пустое тело, если игра не найдена") {
      (mockGameService.find _)
        .expects(1)
        .returns(Future.successful(None))

      Get("/admin/game/1") ~> route ~> check {
        assert(status == StatusCodes.OK)
        assert(responseAs[Option[Game]].isEmpty)
      }
    }
  }


  private val mockGameService = mock[GameService]
  private val route = {
    Route.seal(
      new AdminApi(mockGameService).route
    )(exceptionHandler = HangmanExceptionHandler.exceptionHandler) // ExceptionHandler - обрабатывает ошибки, которые произошли при обработке запроса
  }


  private val sampleGame = hangman.Game(
    id = 1,
    startedAt = Instant.now(),
    state = State(
      name = "player",
      guesses = Set(),
      word = "word"
    ),
    status = GameStatus.InProgress
  )
}
