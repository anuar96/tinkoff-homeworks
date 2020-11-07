package lecture8.assignment

import java.util.concurrent.atomic.AtomicInteger

class CoronaVisitorGuard(maxVisitors: Int) {
  private val currentVisitorsCount = new AtomicInteger(0)

  def tryEnter(): Boolean = {
    val oldValue = currentVisitorsCount
      .getAndUpdate { current =>
        if (current >= maxVisitors) current
        else current + 1
      }

    if (oldValue < maxVisitors) true
    else false
  }

  def getCurrentVisitorsCount(): Int =
    currentVisitorsCount.get()

  def leave(): Int = {
    currentVisitorsCount.decrementAndGet()
  }
}
