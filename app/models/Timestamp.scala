package models

import java.time.LocalDateTime
import play.api.libs.json.*
import scala.util.Try

opaque type Timestamp = LocalDateTime

object Timestamp:
  def apply(ldt: LocalDateTime): Timestamp = ldt
  def now(): Timestamp = LocalDateTime.now()
  
  given Format[Timestamp] = Format[Timestamp](
    json => json.validate[String].flatMap(s => 
      Try(LocalDateTime.parse(s)).fold(
        _ => JsError(s"Invalid timestamp format: $s"),
        t => JsSuccess(Timestamp(t))
      )
    ),
    ts => JsString(ts.toString)
  )
  
  extension (t: Timestamp)
    def isInFuture: Boolean = t.isAfter(LocalDateTime.now())