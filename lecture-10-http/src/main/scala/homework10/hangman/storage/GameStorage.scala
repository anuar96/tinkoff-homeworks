package homework10.hangman.storage

import java.time.Instant

import homework10.hangman.{Game, GameStatus, State}

import scala.concurrent.Future

/**
 * Хранилище игрового состояния
 */
trait GameStorage {

  /**
   * Возвращает игру с данным идентификатором
   *
   * @param id идентификатор игры
   * @return игру или None, если игра не найдена
   */
  def find(id: Long): Future[Option[Game]]

  /**
   * Вставляет новую игру в хранилище, генерируя для нее случайный идентификатор
   *
   * @param startedAt время старта игры
   * @param state состояние игры
   * @param status статус игры
   * @return игру со случайным идентификатором
   */
  def insert(startedAt: Instant,
             state: State,
             status: GameStatus): Future[Game]

  /**
   * Обновляет состояние игры
   * @param game игра для обновления
   * @return обновленную игру или GameNotFoundException, если игра не найдена
   */
  def update(game: Game): Future[Game]

}
