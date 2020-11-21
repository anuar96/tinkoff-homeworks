package homework10.hangman

import enumeratum.{CirceEnum, Enum, EnumEntry}

sealed trait GameStatus extends EnumEntry

object GameStatus extends Enum[GameStatus] with CirceEnum[GameStatus] {

  case object Won extends GameStatus

  case object InProgress extends GameStatus

  case object Lost extends GameStatus

  override val values: IndexedSeq[GameStatus] = findValues
}