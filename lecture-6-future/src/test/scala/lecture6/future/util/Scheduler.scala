package lecture6.future.util

import java.util.concurrent.{ScheduledThreadPoolExecutor, TimeUnit}

import scala.concurrent.duration.FiniteDuration

object Scheduler {
  private val javaScheduler = new ScheduledThreadPoolExecutor(1)

  def executeAfter[U](delay: FiniteDuration)(block: => U): Cancellable = {
    val sf = javaScheduler.schedule(() => block, delay.toMillis, TimeUnit.MILLISECONDS)
    () => {
      sf.cancel(false)
      ()
    }
  }
}

trait Cancellable {
  def cancel(): Unit
}
