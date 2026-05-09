package models

import play.api.libs.json.* 

opaque type AmountInPence = Long

object AmountInPence:
  def apply(value: Long): AmountInPence = value
  def unapply(value: AmountInPence): Option[Long] = Some(value)

  given Format[AmountInPence] = Format(
    _.validate[Long].map(AmountInPence.apply),
    a => JsNumber(a)
  )

  given Ordering[AmountInPence] = Ordering[Long]
  
  extension (a: AmountInPence)
    def >(other: Long): Boolean  = a > other
    def <(other: Long): Boolean  = a < other
    def >=(other: Long): Boolean = a >= other
    def <=(other: Long): Boolean = a <= other

    def toLong: Long = a.toLong