package homework10.hangman.logic
import homework10.hangman.Game

import scala.concurrent.Future

trait GameService {

  /**
   * Возвращает игру
   *
   * @param gameId идентификатор игры
   * @return
   */
  def find(gameId: Long): Future[Option[Game]]

  /**
   * Начинает новую игру
   *
   * @param userName имя игрока
   * @return новая игра
   */
  def startNewGame(userName: String): Future[Game]

  /**
   * Попытка угадать букву в загаданном слове
   *
   * @param id    идентификатор игры
   * @param guess буква
   * @return игра с учетом сделанной попытки
   */
  def makeGuess(id: Long, guess: Char): Future[Game]
}
