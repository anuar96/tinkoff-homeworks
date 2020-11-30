package homework10.hangman

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.{Route, RouteConcatenation}
import com.typesafe.scalalogging.LazyLogging
import homework10.hangman.api.{AdminApi, GameApi, HangmanExceptionHandler}
import homework10.hangman.logic.{DictionaryService, GameService, GameServiceImpl, PlayerGameService, PlayerGameServiceImpl}
import homework10.hangman.storage.{GameStorage, ImMemoryGameStorage}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

object HangmanHttpApp {
  implicit val ac: ActorSystem = ActorSystem()
  implicit val ec: ExecutionContext = ac.dispatcher

  def main(args: Array[String]): Unit = {
    Await.result(HangmanGame().start(), Duration.Inf)
  }
}

case class HangmanGame()(implicit ac: ActorSystem, ec: ExecutionContext) extends LazyLogging {

  private val dictionaryService = new DictionaryService
  private val storage: GameStorage = new ImMemoryGameStorage
  private val gameService: GameService = new GameServiceImpl(dictionaryService, storage)
  private val playerGameService: PlayerGameService = new PlayerGameServiceImpl(gameService)
  private val gameRoute: GameApi = new GameApi(playerGameService)
  private val adminRoute: AdminApi = new AdminApi(gameService)
  private val routes = Route.seal(
    RouteConcatenation.concat(
      gameRoute.route,
      adminRoute.route
    )
  )(
    exceptionHandler = HangmanExceptionHandler.exceptionHandler // Обрабатывает ошибки, возникающие при обработке запроса
  )

  def start(): Future[Http.ServerBinding] =
    Http()
      .newServerAt("localhost", 8080)
      .bind(routes)
      .andThen { case b => logger.info(s"server started at: $b") }
}