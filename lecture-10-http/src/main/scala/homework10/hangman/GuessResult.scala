package homework10.hangman

import enumeratum.{CirceEnum, Enum, EnumEntry}

sealed trait GuessResult extends EnumEntry

  object GuessResult extends Enum[GuessResult] with CirceEnum[GuessResult] {

    case object Won extends GuessResult

    case object Lost extends GuessResult

    case object Correct extends GuessResult

    case object Incorrect extends GuessResult

    case object Unchanged extends GuessResult

    override val values: IndexedSeq[GuessResult] = findValues
  }
