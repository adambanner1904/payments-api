package models.response

import models.*

case class CreatePaymentResponse(
  paymentId: UUID,
  amountInPence: AmountInPence,
  method: PaymentMethod, 
  paymentTime: Datetime 
)