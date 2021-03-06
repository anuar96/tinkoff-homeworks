#  Функциональное программирование

## 1. Игра виселица

В первом задании нужно реализовать игру "Виселица". 
Правила можно почитать тут: https://ru.wikipedia.org/wiki/Виселица_(игра)
На лекции мы рассмотрели основные конструкции функционального программирования, а так же кратко познакомились с 
таким типом, как Task - ленивой альтернативой scala.concurrent.Future.
В данном задании нам не понадобится глубокое понимание того, как работает таск.
Самые важные отличия:
* Task - это описание вычисления, а не само вычисление. Это значит, что `Task(1000)` никаких вычислений не запускает 
  в отличие от `Future(1000)`. Можно сказать, что Task представляет собой ленивое вычисление.
  
* Т.к. Task - это описание вычисления, его нужно отдельно запускать. В момент запуска можно определить, какую стратегию 
  исполнения использовать. Если для Future каждый вызов `Future.apply` приводит к запуску асинхронной задачи в другом потоке, 
  то для `Task.apply` по умолчанию это не так и это можно контролировать прямо из кода. Поэтому такие конструкции, как
  `Task(100).map(_ * 1000)` не будут приводить к дорогим асинхронным вычислениям. Их можно считать быстрыми.
  

* В задании вам понадобятся методы `Task.apply`, `Task.map` и `Task.flatMap`

В задании нужно реализовать все `TODO` в homework.Hangman и проверить, что игра работает корректно.
Запрещено использовать мутабельные переменные, глобальное состояние, и функции с сайд-эффектами


## 2. Validated
На лекции мы познакомились с типом Validated из библиотеки cats. Validated оказался довольно нетипичным типом, т.к.
для него не определен метод `flatMap` с семантикой fail-fast. Вместо него доступен синтаксис `Tuple.mapN`, 
который позволяет аккумулировать ошибки. 
В данном задании предлагается реализовать упрощенную версию типа Validated.
Требуется реализовать:
* Экземпляр тайпкласса ParMappable для Validated
* Синтаксис для mapN, который будет доступен для кортежей размерности 2
* Убедиться, что проходят тесты