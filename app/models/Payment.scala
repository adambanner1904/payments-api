package models

import models.Timestamp
import java.util.UUID
import java.time.LocalDateTime
import play.api.libs.json.* 
import play.api.libs.functional.syntax.*
import models.request.CreatePaymentRequest

import org.mongodb.scala.Document


case class Payment(
  paymentId: Int,
  amountInPence: AmountInPence,
  method: PaymentMethod, 
  paymentTime: Timestamp 
)

object Payment:
  def apply(id: Int, cpr: CreatePaymentRequest): Payment =
    Payment(id, cpr.amountInPence, cpr.method, cpr.paymentTime)
    
  given Format[Payment] = Json.format[Payment]
  
  def fromDocument(doc: Document): Payment = 
    Payment(
      paymentId = doc("paymentId").asInt32.getValue,
      amountInPence = doc("amountInPence").asInt32.getValue,
      method = PaymentMethod.valueOf(doc("method").asString.getValue),
      paymentTime = Timestamp(LocalDateTime.parse(doc("paymentTime").asString.getValue))
    )
  extension (p: Payment)
    def toDocument: Document = 
      Document(
        "paymentId"   -> p.paymentId,
        "amountInPence" -> p.amountInPence,
        "method"        -> p.method.toString,
        "paymentTime"   -> p.paymentTime.toString
      )
    
  