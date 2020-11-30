package homework10.hangman

sealed abstract class HangmanException(message: String) extends Exception(message)

final case class GameNotFoundException(gameId: Long) extends HangmanException(s"Game with id=$gameId not found")

final case class GameAlreadyFinishedException(gameId: Long, status: GameStatus)
  extends HangmanException(s"Game with id=$gameId already finished. You $status!")
