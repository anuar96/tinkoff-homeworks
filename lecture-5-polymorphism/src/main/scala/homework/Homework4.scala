package homework

trait Converter[-S] {
  def convert(value: S): String
}

trait Page[R] {
  def read: (Option[R], Page[R])
}

class Projector[R >: StarredWordLine](converter: Converter[R]){
  def project(screen: Page[R]): String = {
    converter.convert(screen.read._1.getOrElse(new StarredWordLine(0, "end"))) + project(screen.read._2)
    }
}

class WordLine(val word: String)
class StarredWordLine(val stars: Int, override val word: String) extends WordLine(word)

object StarConverter extends Converter[StarredWordLine]{
  override def convert(value: StarredWordLine): String = "*" * value.stars + value.word + "*" * value.stars
}

object LineConverter extends Converter[WordLine] {
  override def convert(value: WordLine): String = value.word + "\n"
}

class HelloPage[R <: WordLine](lines: Seq[R]) extends Page[R]{
  override def read: (Option[R], Page[R]) = (lines.headOption, if (lines.isEmpty) this else new HelloPage(lines.tail))
}

object Main4 extends App{
  new Projector[StarredWordLine](StarConverter).project(new HelloPage(Seq(new StarredWordLine(12,"slide1"),
    new StarredWordLine(3, "slide2"))))

/*
  new Projector[StarredWordLine](StarConverter).project(new HelloPage(Seq(new WordLine("slide1"),
    new WordLine("slide1"))))
*/

  new Projector[StarredWordLine](LineConverter).project(new HelloPage(Seq(new StarredWordLine(12,"slide1"),
    new StarredWordLine(3, "slide2"))))


  new Projector[WordLine](LineConverter).project(new HelloPage(Seq(new StarredWordLine(12,"slide1"),
    new StarredWordLine(3, "slide2"))))

/*
  new Projector[WordLine](StarConverter).project(new HelloPage(Seq(new WordLine("slide1"),
    new WordLine("slide1"))))
*/
}