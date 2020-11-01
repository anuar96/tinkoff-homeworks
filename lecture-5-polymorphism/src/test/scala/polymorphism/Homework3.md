# Привязка типа 2

Вы директор среднеобразовательной школы и как раз сейчас занимаетесь набором учеников в новый класс.
К сожалению, далеко не все ученики одинаково хороши.
Есть дети с разным уровнем знаний и характером, начиная от незнаек KnowNothing и заканчивая гениями Genius.
Связи уровней реализованы в виде иерархии представленной ниже: 
```scala
class KnowNothing
class SlowPerformer extends KnowNothing
class KnowSomething extends KnowNothing
class PoorlyEducated extends KnowSomething
class Normal extends PoorlyEducated
class Enlightened extends Normal
class Genius extends Enlightened
```

Рассмотрим класс SchoolClass:
```scala
class SchoolClass(collection: Seq[KnowNothing]) {
  def accept(students: Seq[KnowNothing]): SchoolClass = new SchoolClass(collection ++ students)
}
```
Вам необходимо его написать/дописать для приема новичков, чтобы SchoolClass имел привязку к знаниям своих учеников.
При этом метод accept должен возвращать новый SchoolClass с наименьшими знаниями/уровнем всех его учеников.
После реализации класс проверить его на примере  - начать класс с гения и "скатиться" до KnowNothing
