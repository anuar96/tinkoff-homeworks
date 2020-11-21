package homework10.hangman.logic

import homework10.hangman._
import homework10.hangman.storage.GameStorage

import scala.concurrent.{ExecutionContext, Future}


class GameServiceImpl(dictionaryService: DictionaryService,
                      gameStorage: GameStorage)(implicit ec: ExecutionContext) extends GameService {

  override def find(gameId: Long): Future[Option[Game]] = ??? // TODO

  override def startNewGame(userName: String): Future[Game] = ??? // TODO

  override def makeGuess(id: Long, guess: Char): Future[Game] = ??? // TODO
}




