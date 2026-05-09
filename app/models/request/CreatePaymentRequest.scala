package models.request

import models.*
import play.api.libs.json.*
import play.api.libs.functional.syntax.*

case class CreatePaymentRequest(
    amountInPence: AmountInPence,
    method: PaymentMethod,
    paymentTime: Timestamp
)

object CreatePaymentRequest:
  given Reads[CreatePaymentRequest] = (
    (JsPath \ "amountInPence")
      .read[AmountInPence]
      .filter(JsonValidationError("amountInPence must be greater than 0"))(amount => 
        amount > 0 && amount < 100_000_000
      ) and
      (JsPath \ "method").read[PaymentMethod] and
      (JsPath \ "paymentTime")
        .read[Timestamp]
        .filter(JsonValidationError("paymentTime cannot be in the future"))(
          !_.isInFuture
        )
  )(CreatePaymentRequest.apply)
