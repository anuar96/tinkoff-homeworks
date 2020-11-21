package homework10.hangman.logic

import homework10.hangman.Game

import scala.concurrent.{ExecutionContext, Future}

/**
 * @inheritdoc homework10.hangman.logic.PlayerGameService
 */
class PlayerGameServiceImpl(delegate: GameService)(implicit ec: ExecutionContext) extends PlayerGameService {
  def find(gameId: Long): Future[Option[Game]] = ??? // TODO

  override def startNewGame(userName: String): Future[Game] = ??? // TODO

  override def makeGuess(id: Long, guess: Char): Future[Game] = ??? // TODO
}
