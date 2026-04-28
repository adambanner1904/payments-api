package repositories

import models.Payment
import models.PaymentMethod.*
import models.Timestamp
import java.time.LocalDateTime
import models.request.CreatePaymentRequest

import scala.concurrent.Future
import javax.inject.{Inject, Singleton}
import org.mongodb.scala.{MongoCollection, MongoDatabase, Document, ObservableFuture}

@Singleton
class PaymentRepository @Inject() (db: MongoDatabase):
  private val collection: MongoCollection[Document] = 
    db.getCollection("payments")
    
  var payments: List[Payment] = List(
    Payment(1,  7500,  PayByBank, Timestamp(LocalDateTime.of(2024, 5, 20, 9,  45, 0))),
    Payment(2,  4999,  Card,      Timestamp(LocalDateTime.of(2024, 1, 5,  9,  15, 0))),
    Payment(3,  1500,  PayByBank, Timestamp(LocalDateTime.of(2024, 1, 12, 11, 42, 0))),
    Payment(4,  8750,  Card,      Timestamp(LocalDateTime.of(2024, 2, 3,  14, 0,  0))),
    Payment(5,  300,   PayByBank, Timestamp(LocalDateTime.of(2024, 2, 17, 8,  30, 0))),
    Payment(6,  12000, Card,      Timestamp(LocalDateTime.of(2024, 3, 1,  16, 55, 0))),
    Payment(7,  650,   PayByBank, Timestamp(LocalDateTime.of(2024, 3, 22, 10, 10, 0))),
    Payment(8,  9999,  Card,      Timestamp(LocalDateTime.of(2024, 4, 8,  13, 25, 0))),
    Payment(9,  450,   PayByBank, Timestamp(LocalDateTime.of(2024, 4, 19, 17, 5,  0))),
    Payment(10, 3200,  Card,      Timestamp(LocalDateTime.of(2024, 5, 6,  12, 0,  0)))
  )
  
  def allPayments: Future[Seq[Payment]] = 
    collection
      .find()
      .map(Payment.fromDocument)
      .toFuture()

  def save(payment: CreatePaymentRequest): Unit = 
    payments = payments :+ Payment(lastId + 1, payment)
    
  def lastId: Int = payments.maxBy(_.paymentId).paymentId
