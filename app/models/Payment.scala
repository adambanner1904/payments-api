package models

import play.api.libs.json.* 


case class Payment(
  id: String,
  amountInPence: AmountInPence,
  method: PaymentMethod, 
  paymentTime: Timestamp 
)

object Payment:
  given Format[Payment] = Json.format[Payment]
