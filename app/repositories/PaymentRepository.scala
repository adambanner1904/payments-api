package repositories

import models.*
import models.request.CreatePaymentRequest
import java.time.LocalDateTime

import scala.concurrent.{Future, ExecutionContext}
import org.mongodb.scala.*

import javax.inject.{Inject, Singleton}
import org.mongodb.scala.model.Filters
import org.bson.types.ObjectId

@Singleton
class PaymentRepository @Inject() (db: MongoDatabase)(using ExecutionContext):
  private val collection: MongoCollection[Document] =
    db.getCollection("payments")

  def allPayments: Future[Seq[Payment]] =
    collection
      .find()
      .map(fromDocument)
      .toFuture()

  def findPayment(oid: ObjectId): Future[Option[Payment]] =
        collection
          .find(Filters.eq("_id", oid))
          .headOption()
          .map(_.map(fromDocument))

  def save(cpr: CreatePaymentRequest): Future[Payment] =
    val doc = toDocument(cpr)
    collection
      .insertOne(doc)
      .toFuture()
      .map: result =>
        val id = result.getInsertedId().asObjectId().getValue().toString()
        Payment(id, cpr.amountInPence, cpr.method, cpr.paymentTime)

  private def fromDocument(doc: Document): Payment = 
    Payment(
      id = doc("_id").asObjectId().getValue.toString(),
      amountInPence = AmountInPence(doc("amountInPence").asInt32.getValue),
      method = PaymentMethod.valueOf(doc("method").asString.getValue),
      paymentTime = Timestamp(LocalDateTime.parse(doc("paymentTime").asString.getValue))
    )
    
  def toDocument(p: CreatePaymentRequest): Document =
    Document(
      "createdAt" -> Timestamp.now().toString,
      "auditSource" -> "payments-api",
      "schemaVersion" -> 1,
      "amountInPence" -> p.amountInPence.toLong,
      "method" -> p.method.toString,
      "paymentTime" -> p.paymentTime.toString
    )