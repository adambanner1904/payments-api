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
    (JsPath \ "amountInPence").read[AmountInPence]
      .filter(JsonValidationError("amountInPence must be greater than 0"))(_ > 0) and 
      (JsPath \ "method").read[PaymentMethod] and 
        (JsPath \ "paymentTime").read[Timestamp]
          .filter(JsonValidationError("paymentTime cannot be in the future"))(!_.isInFuture)
  )(CreatePaymentRequest.apply)
  
  given Writes[CreatePaymentRequest] = request => Json.obj(
    "amountInPence" -> request.amountInPence.toInt,
    "method" -> request.method,
    "paymentTime" -> request.paymentTime.toString
  )