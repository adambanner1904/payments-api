package models.request

import models.*

case class CreatePaymentRequest(
  amountInPence: AmountInPence,
  method: Method, 
  paymentTime: Datetime 
)