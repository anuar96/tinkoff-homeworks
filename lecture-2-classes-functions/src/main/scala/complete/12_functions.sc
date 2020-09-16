import scala.annotation.tailrec
// Рекурсивные функции

// Фибоначчи
def calcSalary(level: Int, initial: Int): Int = {
  if(level <= 1) initial
  else calcSalary(level - 1, initial) + calcSalary(level - 2, initial)

}

calcSalary(8, 100)


def calcSalaryTailrec(level: Int, initial: Int): Int = {

  @tailrec
  def calcSalaryTailrecIter(currentLevel: Int, prev: Int, current: Int): Int = {
    if (currentLevel == level) current
    else calcSalaryTailrecIter(currentLevel + 1, current, prev + current)
  }
  calcSalaryTailrecIter(level, initial, 0)

}

calcSalary(8, 100)
