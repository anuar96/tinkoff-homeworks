package homework10.hangman.api

import java.time.Instant

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.testkit.ScalatestRouteTest
import homework10.hangman
import homework10.hangman.logic.PlayerGameService
import homework10.hangman.{Game, GameAlreadyFinishedException, GameNotFoundException, GameStatus, State}
import org.scalamock.scalatest.MockFactory
import org.scalatest.funspec.AnyFunSpec

import scala.concurrent.Future

class GameApiSpec extends AnyFunSpec with MockFactory with ScalatestRouteTest {

  import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._

  describe("GET http://localhost:8080/game/{gameId}") {
    it("возвращает текущее состояние игры с маскированным словом") {
      (mockGameService.find _)
        .expects(1)
        .returns(Future.successful(Some(sampleGame)))

      Get("/game/1") ~> route ~> check {
        assert(status == StatusCodes.OK)
        assert(responseAs[Option[Game]].contains(sampleGame))
      }
    }

    it("возвращает пустое тело, если игра не найдена") {
      (mockGameService.find _)
        .expects(1)
        .returns(Future.successful(None))

      Get("/game/1") ~> route ~> check {
        assert(status == StatusCodes.OK)
        assert(responseAs[Option[Game]].isEmpty)
      }
    }
  }

  describe("POST http://localhost:8080/game/{gameId}/guess?letter={guess}") {
    it("возвращает измененную игру") {
      (mockGameService.makeGuess _)
        .expects(1, 'a')
        .returns(Future.successful(sampleGame))

      Post("/game/1/guess?letter=a") ~> route ~> check {
        assert(status == StatusCodes.OK)
        assert(responseAs[Game] == sampleGame)
      }
    }

    it("возвращает код 400 и текст ошибки в виде json если игра не найдена") {
      (mockGameService.makeGuess _)
        .expects(1, 'a')
        .returns(Future.failed(GameNotFoundException(1)))

      Post("/game/1/guess?letter=a") ~> route ~> check {
        assert(status == StatusCodes.BadRequest)
        assert(responseAs[ExceptionResponse] == ExceptionResponse("Game with id=1 not found"))
      }
    }

    it("возвращает код 400 и текст ошибки в виде json если игра уже окончена") {
      (mockGameService.makeGuess _)
        .expects(1, 'a')
        .returns(Future.failed(GameAlreadyFinishedException(1, GameStatus.Won)))

      Post("/game/1/guess?letter=a") ~> route ~> check {
        assert(status == StatusCodes.BadRequest)
        assert(responseAs[ExceptionResponse] == ExceptionResponse("Game with id=1 already finished. You Won!"))
      }
    }
  }

  describe("POST game?userName={playerName}") {
    it("возвращает новую игру") {
      (mockGameService.startNewGame _)
        .expects("player")
        .returns(Future.successful(sampleGame))

      Post("/game?userName=player") ~> route ~> check {
        assert(status == StatusCodes.OK)
        assert(responseAs[Game] == sampleGame)
      }
    }
  }

  private val mockGameService: PlayerGameService = mock[PlayerGameService]
  private val route = Route.seal(
    new GameApi(mockGameService).route
  )(exceptionHandler = HangmanExceptionHandler.exceptionHandler) // ExceptionHandler - обрабатывает ошибки, которые произошли при обработке запроса

  private val sampleGame = hangman.Game(
    id = 1,
    startedAt = Instant.now(),
    state = State(
      name = "player",
      guesses = Set(),
      word = "a***"
    ),
    status = GameStatus.InProgress
  )
}
