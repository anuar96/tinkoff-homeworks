package homework10.hangman.storage

import java.time.Instant
import java.util.concurrent.ThreadLocalRandom

import homework10.hangman
import homework10.hangman.{Game, GameNotFoundException, GameStatus, State}

import scala.collection.concurrent.TrieMap
import scala.concurrent.Future


/**
 * In-memory хранилище для состояния игры на основе TrieMap
 */
class ImMemoryGameStorage extends GameStorage {
  override def find(id: Long): Future[Option[Game]] = ??? // TODO

  override def insert(startedAt: Instant,
                      state: State,
                      status: GameStatus): Future[Game] = ??? // TODO

  override def update(game: Game): Future[Game] = ??? // TODO

  private val storage = TrieMap[Long, Game]()
}
