package collections

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import crutch.NothingFixes

/**
 * Задания среднего уровня - требуют немного подумать,
 * но решение достаточно прямолинейно вытекает из постановки.
 *
 * Все задания необходимо решать используя иммутабельные коллекции,
 * т.е. scala.collection._ и scala.collection.immutable._
 *
 * Для запуска тестов только в этом файле: `sbt testOnly *.Collections2_Normal`
 */
class Collections2_Normal extends AnyFunSuite with Matchers with NothingFixes{

  // Это задание необходимо выполнить без использования методов конкатенации коллекций (:::, ++ etc)
  // Что должно получиться - можно видеть в тестовых данных.
  // Выполнение должно быть за линейное время
  test("Объединение списков поэлементно") {
    val list1 = List(1, 2, 3)
    val list2 = List(4, 5, 6)

    val `list1 concat list2`: List[Int] = List(list1, list2).flatten
    val `reversed list1 concat list2` = List(list1.reverse, list2).flatten
    val `list1 concat reversed list2` = List(list1, list2.reverse).flatten

    `list1 concat list2` shouldBe List(1, 2, 3, 4, 5, 6)
    `reversed list1 concat list2` shouldBe List(3, 2, 1, 4, 5, 6)
    `list1 concat reversed list2` shouldBe List(1, 2, 3, 6, 5, 4)
  }

  // В списке чисел нужно найти число с самым большим числом повторений, и вернуть новый список без этого числа
  // Если есть несколько разных чисел с одинаковой (максимальной) частотой, то удалить их все
  test("Удаление самого частого числа") {
    def removeMostFrequent(numbers: Seq[Int]): Seq[Int] = {
      val repeatRate: Map[Int, Int] = numbers.groupBy(identity).mapValues(_.size)
      repeatRate match {
        case _ if repeatRate.isEmpty => Seq()
        case _ =>
          val maxRepeteadCount = repeatRate.maxBy(_._2)._2
          val maxRepeteadKeys = repeatRate.filter { case (_, b) => b == maxRepeteadCount }.keys.toSeq
          numbers.filterNot(maxRepeteadKeys.contains(_))
      }
    }

    removeMostFrequent(Seq()) shouldBe Seq()
    removeMostFrequent(Seq(1)) shouldBe Seq()
    removeMostFrequent(Seq(1, 2, 3, 2, 4, 3, 2)) shouldBe Seq(1, 3, 4, 3)
    removeMostFrequent(Seq(1, 2, 3, 2, 4, 3, 2, 3)) shouldBe Seq(1, 4)
  }

  // Для каждого элемента списка, нужно заменить его на среднее арифметическое этого элемента и двух соседних
  // Если какого-то из соседних элементов нет, то среднее необходимо считать не по 3, а по 2 или 1 значению.
  test("Сглаживание списка чисел") {
    def smoothNumbers(numbers: Seq[Int]) = {
      val newNumbers = None +: numbers.map(Some(_)) :+ None
      newNumbers.sliding(3, 1).toSeq.flatMap {
        case Seq(None, Some(b), None) => Seq(b.toDouble)
        case Seq(None, Some(b), Some(c)) => Seq((b + c).toDouble / 2)
        case Seq(Some(a), Some(b), Some(c)) => Seq((a + b + c).toDouble / 3)
        case Seq(Some(a), Some(b), None) => Seq((a + b).toDouble / 2)
        case Seq(None, None) => Seq()
      }
    }

    smoothNumbers(Seq()) shouldBe Seq()
    smoothNumbers(Seq(3)) shouldBe Seq(3.0d)
    smoothNumbers(Seq(1, 4)) shouldBe Seq(2.5d, 2.5d)
    smoothNumbers(Seq(1, 9, 5, 4, 3, 5, 5, 5, 8, 2)) shouldBe Seq(5.0, 5.0, 6.0, 4.0, 4.0, 13.0 / 3, 5.0, 6.0, 5.0, 5.0)
  }

  // Есть список людей (фамилия, имя, отчество)
  // Нужно для каждого человека поменять его имя на отличное имя среди однофамильцев
  // Если подходящих имен, отличных от настоящего нет, то оставить прежнее имя.
  //
  // Например если в списке есть Иван Смирнов и Андрей Смирнов, то Иван должен стать Андреем,
  // а Андрей - Иваном (т.к. их всего двое с одной фамилией)
  // Если у человека больше 1 однофамильца, то выбрать у них любое отличающееся имя
  // 
  // При этом имя одного человека может быть выбрано более одного раза (не удаляется из доступных при выборе)
  // Например если в списка два Ивана Смирновых и Андрей Смирнов, то оба Ивана должны стать Андреями.
  test("Смена имен") {
    case class User(lastName: String, firstName: String, middleName: String)

    def swapNames(users: Seq[User]): Seq[User] = {
      users.map { user =>
        users.collectFirst {
          case User(user.lastName, firstName, middleName) if firstName != user.firstName =>
            user.copy(firstName = firstName)
        }.getOrElse(user)
      }
    }

    val original = Seq(
      User("Иванов", "Иван", "Иванович"),
      User("Иванов", "Петр", "Петрович"),
      User("Петров", "Василий", "Абрамович"),
      User("Сидоров", "Александр", "Григорьевич"),
      User("Сидоров", "Артем", "Викторович"),
      User("Сидоров", "Виктор", "Львович"),
      User("Сидоров", "Виктор", "Александрович")
    )
    val swapped = swapNames(original)

    swapped should contain allElementsOf (Seq(
      User("Иванов", "Петр", "Иванович"),
      User("Иванов", "Иван", "Петрович"),
      User("Петров", "Василий", "Абрамович")
    ))
    swapped should contain oneOf(User("Сидоров", "Артем", "Григорьевич"), User("Сидоров", "Виктор", "Григорьевич"))
    swapped should contain oneOf(User("Сидоров", "Александр", "Викторович"), User("Сидоров", "Виктор", "Викторович"))
    swapped should contain oneOf(User("Сидоров", "Александр", "Львович"), User("Сидоров", "Артем", "Львович"))
    swapped should not contain atLeastOneElementOf(original.filterNot(_.lastName == "Петров"))

  }

  // Есть список людей (фамилия, имя, отчество, возраст)
  // Нужно отсортировать его в следующем порядке: фамилия (лекс) -> возраст (по убыванию) -> имя (лекс) -> отчество (лекс)
  test("Сортировка по фамилии и возрасту") {
    case class User(lastName: String, firstName: String, middleName: String, age: Int)

    def sortUsers(users: Seq[User]): Seq[User] = {
      users.sortBy(r => (r.lastName, -r.age, r.firstName, r.middleName))
    }

    val sorted = sortUsers(
      Seq(
        User("Сидоров", "Виктор", "Львович", 30),
        User("Иванов", "Петр", "Петрович", 42),
        User("Петров", "Василий", "Абрамович", 8),
        User("Сидоров", "Александр", "Григорьевич", 30),
        User("Иванов", "Иван", "Иванович", 34),
        User("Сидоров", "Артем", "Викторович", 33)
      )
    )

    sorted shouldBe Seq(
      User("Иванов", "Петр", "Петрович", 42),
      User("Иванов", "Иван", "Иванович", 34),
      User("Петров", "Василий", "Абрамович", 8),
      User("Сидоров", "Артем", "Викторович", 33),
      User("Сидоров", "Александр", "Григорьевич", 30),
      User("Сидоров", "Виктор", "Львович", 30)
    )
  }
}
