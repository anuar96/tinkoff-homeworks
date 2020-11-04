package lecture8.assignment

import java.time.Instant

sealed trait Status
object Status {
  case class On(enabledAt: Instant) extends Status
  case class Off(enabledAt: Instant) extends Status
}

class Teapot {
  private var turnedOn: Boolean = true
  private var turnedOnAt: Option[Instant] = Some(Instant.now())
  private var turnedOffAt: Option[Instant] = None

  def turnOff(): Unit = synchronized {
    turnedOn = false
    turnedOnAt = None
    turnedOffAt = Some(Instant.now())
  }

  def turnOn(): Unit = synchronized {
    turnedOn = true
    turnedOnAt = Some(Instant.now())
    turnedOffAt = None
  }

  def getStatus: Status =  synchronized {
    if (turnedOn) Status.On(turnedOnAt.get)
    else Status.Off(turnedOffAt.get)
  }
}
