package models.response

import models.*
import java.util.UUID

case class CreatePaymentResponse(
  paymentId: UUID,
  amountInPence: AmountInPence,
  method: PaymentMethod, 
  paymentTime: Timestamp 
)