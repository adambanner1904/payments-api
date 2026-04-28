package modules

import javax.inject.{Inject, Singleton}
import org.mongodb.scala.{MongoDatabase, Document, ObservableFuture}
import scala.concurrent.Await
import scala.concurrent.duration.*

@Singleton
class MongoSeeder @Inject() (db: MongoDatabase):
  private val collection = db.getCollection("payments")
  
  private val seedData = Seq(
     Document("paymentId" -> 1,  "amountInPence" -> 7500,  "method" -> "PayByBank", "paymentTime" -> "2024-05-20T09:45:00"),
     Document("paymentId" -> 2,  "amountInPence" -> 4999,  "method" -> "Card",      "paymentTime" -> "2024-01-05T09:15:00"),
     Document("paymentId" -> 3,  "amountInPence" -> 1500,  "method" -> "PayByBank", "paymentTime" -> "2024-01-12T11:42:00"),
     Document("paymentId" -> 4,  "amountInPence" -> 8750,  "method" -> "Card",      "paymentTime" -> "2024-02-03T14:00:00"),
     Document("paymentId" -> 5,  "amountInPence" -> 300,   "method" -> "PayByBank", "paymentTime" -> "2024-02-17T08:30:00"),
     Document("paymentId" -> 6,  "amountInPence" -> 12000, "method" -> "Card",      "paymentTime" -> "2024-03-01T16:55:00"),
     Document("paymentId" -> 7,  "amountInPence" -> 650,   "method" -> "PayByBank", "paymentTime" -> "2024-03-22T10:10:00"),
     Document("paymentId" -> 8,  "amountInPence" -> 9999,  "method" -> "Card",      "paymentTime" -> "2024-04-08T13:25:00"),
     Document("paymentId" -> 9,  "amountInPence" -> 450,   "method" -> "PayByBank", "paymentTime" -> "2024-04-19T17:05:00"),
     Document("paymentId" -> 10, "amountInPence" -> 3200,  "method" -> "Card",      "paymentTime" -> "2024-05-06T12:00:00")
   )
   
   Await.result(
    collection.drop().toFuture().flatMap(_ => 
      collection.insertMany(seedData).toFuture()
    )(using scala.concurrent.ExecutionContext.global),
    10.seconds
   )