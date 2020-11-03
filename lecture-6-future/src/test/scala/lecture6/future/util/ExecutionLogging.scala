package lecture6.future.util

import java.time.LocalDateTime

import com.typesafe.scalalogging.Logger


trait ExecutionLogging {
  protected def logger: Logger

  def withExecutionLogging[A](tag: String)
                             (block: => A): A = {
    val start = System.currentTimeMillis()
    logger.trace(s"Begin task $tag at ${LocalDateTime.now}")
    val result = block
    val end = System.currentTimeMillis()
    logger.trace(s"End task $tag at ${LocalDateTime.now} in ${end - start} ms")
    result
  }
}
