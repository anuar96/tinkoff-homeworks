package homework7

import org.scalatest.funspec.AnyFunSpec

case class ValidationError(error: String) extends Exception(error) {
  override def toString(): String = error
}

import ParMappable._

class ParMappableForValidatedTest extends AnyFunSpec {
  describe("map2") {
    it("accumulates errors if all effects failed") {
      val barError = ValidationError("bar")
      val fooError = ValidationError("foo")

      assert(
        implicitly[ParMappable[Validated]]
          .map2(
            Validated.invalid[Int](fooError),
            Validated.invalid[Int](barError),
          )(_ + _) == Invalid(List(fooError, barError))
      )
    }

    it("apply function f on both success") {
      assert(
        implicitly[ParMappable[Validated]]
          .map2(
            Validated.valid(10),
            Validated.valid(20)
          )(_ + _) == Valid(30)
      )
    }

    it("returns first error") {
      val fooError = ValidationError("foo")

      assert(
        implicitly[ParMappable[Validated]]
          .map2(
            Validated.invalid[Int](fooError),
            Validated.valid(10)
          )(_ + _) == Invalid(List(fooError))
      )
    }

    it("return returns seconds error") {
      val fooError = ValidationError("foo")

      assert(
        implicitly[ParMappable[Validated]]
          .map2(
            Validated.valid(10),
            Validated.invalid[Int](fooError),
          )(_ + _) == Invalid(List(fooError))
      )
    }
  }

  describe("new") {
    it("return Valid") {
      assert(
        implicitly[ParMappable[Validated]].`new`(10) == Validated.valid(10)
      )
    }
  }

  describe("mapN") {
    it("available on tuple") {
      assert(
        (
          Validated.valid(10),
          Validated.valid(20)
          ).mapN(_ + _) == Validated.valid(30)
      )
    }
  }
}
