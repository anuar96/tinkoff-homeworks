# Домашнее задание

## Future и Promise
### 1. Реализовать все методы в классе `lecture6.future.assignment.Assignment`
### 2. Написать тесты в `lecture6.future.test.AssignmentTest`.

В реализации может пригодиться `lecture6.future.assignment.util.Scheduler.executeAfter`
Для реализации тестов, где нужно считать попытки, вам может пригодиться класс java.util.concurrent.atomic.AtomicInteger.
На основе этого класса удобно реализовывать счетчики в конкурентных программах. Мутировать данные из конкурентных программ 
в общем случае - небезопасно.

## Тайпклассы в scala
### 2. Recoverable для Future и Try 
На лекции мы попытались найти общее между стандартными типами в scala, такими как Option, Try, Future и тд.
Оказалось, что у них у всех есть методы `map`, `flatMap` и эти методы ведут себя одинаково с точки зрения интерфейсов.
Но `map` и `flatMap` - это не единственные методы, которые связывают стандартные "контейнеры" в scala. В этом задании 
предлагается реализовать lecture6.typeclass `Recoverable` - тайпкласс для контейнеров, вычисления в которых могут завершаться ошибками.
Примеры таких контейнеров в стандартной библиотеке scala: `Future[_]`, `Try[_]`, `Either[Throwable, _]` 

1. Реализовать lecture6.typeclass `lecture6.typeclass.Recoverable` для Future и Try
2. Добавить синтаксис для тайпкласса, чтобы тест `lecture6.typeclass.RecoverableTest` начал компилироваться
3. Тесты `lecture6.typeclass.RecoverableFutureTest` и `lecture6.typeclass.RecoverableTryTest` должны проходить

### 2.1 Recoverable для CompletableFuture
Повышенный уровень сложности. Не обязательно к выполнению

Не лекции мы выяснили, что с помощью тайпклассов можно объединить под один интерфейс не только типы из стандартной библиотеки scala,
но и типы, которые вообще являются не scala-классами! На лекции мы реализовали тайпкласс `FlatMap` для CompletableFuture.
В этом задании нужно реализовать тайпкласс `Recovarable` для `java.util.concurrent.CompletableFuture` и покрыть 
его тестами аналогично заданию выше
 