package homework

trait KnowledgeLevel

class KnowNothing extends KnowledgeLevel
class SlowPerformer extends KnowNothing with KnowledgeLevel
class KnowSomething extends KnowNothing with KnowledgeLevel
class PoorlyEducated extends KnowSomething with KnowledgeLevel
class Normal extends PoorlyEducated with KnowledgeLevel
class Enlightened extends Normal with KnowledgeLevel
class Genius extends Enlightened with KnowledgeLevel


class SchoolClass[T <: KnowNothing](collection: Seq[T]) {

  def accept[R >: T <: KnowNothing](students: Seq[R]): SchoolClass[R] = {
    new SchoolClass[R](collection ++ students)
  }

}

object Main3 extends App{
  val schoolClass = new SchoolClass[Genius](Seq(new Genius, new Genius))
  val class2: SchoolClass[KnowNothing] = schoolClass.accept(Seq(new Enlightened))
    .accept(Seq(new Enlightened, new Normal))
    .accept(Seq(new Enlightened, new Normal, new PoorlyEducated))
    .accept(Seq(new PoorlyEducated, new Genius, new KnowNothing))
    .accept(Seq(new SlowPerformer, new KnowNothing, new Enlightened))
}