package homework10.hangman.logic

import homework10.hangman.storage.Dictionary.Dictionary

import scala.util.Random

class DictionaryService {
  def chooseWord(): String =
    Dictionary(Random.nextInt(Dictionary.length))

  def listAll(): Seq[String] = Dictionary
}
