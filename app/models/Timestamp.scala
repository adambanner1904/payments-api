package models

import java.time.LocalDateTime
import play.api.libs.json.*

opaque type Timestamp = LocalDateTime

object Timestamp:
  def apply(ldt: LocalDateTime): Timestamp = ldt
  def now(): Timestamp = LocalDateTime.now()
  
  given Format[Timestamp] = Format[Timestamp](
    _.validate[String].map(LocalDateTime.parse),
    ts => JsString(ts.toString)
  )
  
  extension (t: Timestamp)
    def isInFuture: Boolean = t.isAfter(LocalDateTime.now())