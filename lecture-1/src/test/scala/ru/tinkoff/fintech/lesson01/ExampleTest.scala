package ru.tinkoff.fintech.lesson01

import org.scalatest.flatspec.AnyFlatSpecLike
import org.scalatest.matchers.should.Matchers

class ExampleTest extends AnyFlatSpecLike with Matchers {
  it should "pass test" in {
    1 shouldBe 1
  }

  it should "pass new test" in {
    2 shouldBe 2
  }
}
