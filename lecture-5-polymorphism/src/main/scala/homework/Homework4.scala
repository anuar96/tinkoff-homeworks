package homework

trait Converter[-S] {
  def convert(value: S): String
}

trait Page[R] {
  def read: (Option[R], Page[R])
}

class Projector[R](converter: Converter[R]) {
  def project(screen: Page[R]): String = {
    screen.read match {
      case (None, _) => ""
      case (Some(r), nextPage) => converter.convert(r) + project(nextPage)
    }
  }
}

class WordLine(val word: String)

class StarredWordLine(val stars: Int, override val word: String) extends WordLine(word)

object StarConverter extends Converter[StarredWordLine] {
  override def convert(value: StarredWordLine): String = "*" * value.stars + value.word + "*" * value.stars
}

object LineConverter extends Converter[WordLine] {
  override def convert(value: WordLine): String = value.word + "\n"
}

class HelloPage[R <: WordLine](lines: Seq[R]) extends Page[R] {
  override def read: (Option[R], Page[R]) = lines match {
    case head :: tail =>(Some(head), new HelloPage(tail))
    case Nil => (None, this)
  }
}

object Main4 extends App {
  new Projector[StarredWordLine](StarConverter).project(new HelloPage(Seq(new StarredWordLine(12, "slide1"),
    new StarredWordLine(3, "slide2"))))

  /*
    new Projector[StarredWordLine](StarConverter).project(new HelloPage(Seq(new WordLine("slide1"),
      new WordLine("slide1"))))
  */

  new Projector[StarredWordLine](LineConverter).project(new HelloPage(Seq(new StarredWordLine(12, "slide1"),
    new StarredWordLine(3, "slide2"))))

  new Projector[WordLine](LineConverter).project(new HelloPage(Seq(new StarredWordLine(12, "slide1"),
    new StarredWordLine(3, "slide2"))))

  new Projector[WordLine](LineConverter).project(new HelloPage(Seq(new WordLine("slide1"),
    new WordLine("slide1"))))


  /*    new Projector[WordLine](StarConverter).project(new HelloPage(Seq(new WordLine("slide1"),
        new WordLine("slide1"))))*/
}