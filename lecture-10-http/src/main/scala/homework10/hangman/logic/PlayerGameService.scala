package homework10.hangman.logic

/**
 * Игровой сервис, предназначенный для взаимодействия с игроком.
 * Перед тем, как отдать игру, маскирует слово - заменяет все не угаданные буквы на '*'
 */
trait PlayerGameService extends GameService