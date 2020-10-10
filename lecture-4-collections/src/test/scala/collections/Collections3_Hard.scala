package collections

import org.scalatest.matchers.should.Matchers
import org.scalatest.funsuite.AnyFunSuite

/**
  * Задания сложного уровня - вероятно придется поломать голову и проявить изобретательность
  *
  * Все задания необходимо решать используя иммутабельные коллекции,
  * т.е. scala.collection._ и scala.collection.immutable._
  * 
  * Для запуска тестов только в этом файле: `sbt testOnly *.Collections3_Hard`
  */
class Collections3_Hard extends AnyFunSuite with Matchers {

  /* Нужно описать метод, который будет сортировать в одном списке четные числа - по возрастанию,
   *  а нечетные - по убыванию; при этом позиции, занимаемые четными и нечетными числами должны сохраняться.
   *  Т.е. если в исходном списке на позиции N было четное число, то в результирующем также будет четное,
   *  и аналогично - для нечетных.
   *
   * Алгоритмическая сложность метода должна быть не больше, чем у алгоритма сортировки из стандартной библиотеки
   */
  test("Сортировка четных и нечетных чисел в разную сторону") {
    def sortEvenUpOddDown(numbers: Seq[Int]): Seq[Int] = {
      val evens: IndexedSeq[Int] = numbers.filter(_ % 2 == 0).sorted.toIndexedSeq
      val odds: IndexedSeq[Int] = numbers.filter(_ % 2 == 1).sortBy(-_).toIndexedSeq

      var oddsIndex = 0
      var evensIndex = 0
      numbers.map{ number =>
        if (number % 2 == 1){
          oddsIndex = oddsIndex + 1
          odds(oddsIndex - 1)
        }
        else{
          evensIndex = evensIndex + 1
          evens(evensIndex - 1)
        }
      }
    }

    sortEvenUpOddDown(Seq()) shouldBe Seq()
    sortEvenUpOddDown(Seq(5)) shouldBe Seq(5)
    sortEvenUpOddDown(Seq(2)) shouldBe Seq(2)
    sortEvenUpOddDown(Seq(4, 2, 6, 4)) shouldBe Seq(2, 4, 4, 6)
    sortEvenUpOddDown(Seq(5, 7, 7, 3)) shouldBe Seq(7, 7, 5, 3)
    sortEvenUpOddDown(Seq(1, 2, 3, 4, 5, 6)) shouldBe Seq(5, 2, 3, 4, 1, 6)
    sortEvenUpOddDown(Seq(1, 4, 2, 3, 5, 5, 6, 9, 7, 3)) shouldBe Seq(9, 2, 4, 7, 5, 5, 6, 3, 3, 1)
    sortEvenUpOddDown(Seq(3, 6, 5, 4, 1, 2, 0, 6, 4, 2, 4, 3)) shouldBe Seq(5, 0, 3, 2, 3, 2, 4, 4, 4, 6, 6, 1)
  }

  // Тренер покемонов Анатолий, обнаружил что может эволюционировать своих покемонов.
  // Нужно посчитать сколько (максимум) уникальных покемонов сможет собрать Анатолий,
  // с учетом того что покемонов в местном дворе он может ловить в любом количестве.
  test("Gotta catch em' all!") {
    val `local pokemon types` = Set("caterpie", "rattata", "pidgey", "pikachu", "diglett")
    val evolutionTable = Map(
      "bulbasaur" -> "ivysaur",
      "ivysaur" -> "venusaur",
      "charmander" -> "charmeleon",
      "charmeleon" -> "charizard",
      "squirtle" -> "wartotle",
      "wartotle" -> "blastoise",
      "caterpie" -> "metapod",
      "metapod" -> "butterfree",
      "rattata" -> "ratticate",
      "pidgey" -> "pidgeotto",
      "pidgeotto" -> "pidgeot",
      "pikachu" -> "raichu",
      "diglett" -> "dugtrio",
      "weedle" -> "kakuna",
      "kakuna" -> "beedrill"
    )

    val anatolyPokemons = Seq("bulbasaur", "charmander", "caterpie", "pidgey", "weedle", "squirtle", "squirtle", "rattata", "pidgey", "rattata", "weedle", "kakuna")

    val maxEvolved: Int = ??? // TODO

    maxEvolved shouldBe 19
  }
}
