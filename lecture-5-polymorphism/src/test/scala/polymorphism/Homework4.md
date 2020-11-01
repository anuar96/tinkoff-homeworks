# Contrvariance & covariance 

В задаче присутствует проектор Projector, на котором мы проецируем переданные страницы.
Страницу Page можно читать, получая за раз следующее значение/токен R для проекции и страницу без этого токена.
Проектор использует Converter для преобразования токена в отображаемую строку
```scala
trait Converter[S] {
  def convert(value: S): String
}

trait Page[R] {
  def read: (Option[R], Page[R])
}

class Projector[R](converter: Converter[R]){
  def project(screen: Page[R]): String = ???
}

class WordLine(val word: String)
class StarredWordLine(val stars: Int, word: String) extends ???

object LineConverter extends Converter[WordLine] {
  override def convert(value: WordLine): String = value.word + "\n"
}

class HelloPage[R <: WordLine](lines: Seq[R]) extends Page[R]
```

1. Написать конвертер Converter[StarredWordLine] добавляющий starr звезд в начале и в конце word
1. Реализовать класс HelloPage принимающий в конструктор список токенов R и отдающий их последовательно через метод read
1. В проекторе для StarredWordLine можно проецировать Page[StarredWordLine], но нельзя Page[WordLine]
1. В проекторе для WordLine можно проецировать Page[WordLine] и Page[StarredWordLine]
1. В проекторе для StarredWordLine можно использовать Converter[StarredWordLine] и Converter[WordLine]
1. В проекторе для WordLine можно использовать Converter[WordLine], но нельзя Converter[StarredWordLine]
1. Показать работу проектора для пунктов 3-6