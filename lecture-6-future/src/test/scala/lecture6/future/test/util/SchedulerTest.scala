package lecture6.future.test.util

import java.util.concurrent.atomic.AtomicBoolean

import lecture6.future.util.Scheduler
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.concurrent.duration._

class SchedulerTest extends AnyFlatSpec with Matchers {
  "scheduler" should "execute block after a delay" in {
    val complete = new AtomicBoolean()
    Scheduler.executeAfter(1.second)(complete.set(true))

    Thread.sleep(200)
    complete.get shouldBe false

    Thread.sleep(1500)
    complete.get shouldBe true
  }
}
