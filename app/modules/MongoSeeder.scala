package modules

import scala.concurrent.*
import javax.inject.{Inject, Singleton}
import org.mongodb.scala.{MongoDatabase, Document, ObservableFuture}
import org.bson.types.ObjectId
import org.mongodb.scala.model.{IndexModel, Indexes}

@Singleton
class MongoSeeder @Inject() (
    db: MongoDatabase,
)(using ExecutionContext):

  private val collection = db.getCollection("payments")
  private def createIndexes(): Future[Unit] = 
    collection.createIndexes(Seq(
      IndexModel(Indexes.ascending("paymentId")), 
      IndexModel(Indexes.ascending("method", "paymentTime"))
    )).toFuture().map(_ => ())
    
  private val seedData = Seq(
    Document(
      "_id" -> new ObjectId("6649d080f3a2b1c4e7d89001"),
      "amountInPence" -> 7500,
      "method" -> "PayByBank",
      "paymentTime" -> "2024-05-20T09:45:00"
    ),
    Document(
      "_id" -> new ObjectId("659868f0f3a2b1c4e7d89002"),
      "amountInPence" -> 4999,
      "method" -> "Card",
      "paymentTime" -> "2024-01-05T09:15:00"
    ),
    Document(
      "_id" -> new ObjectId("659f4c30f3a2b1c4e7d89003"),
      "amountInPence" -> 1500,
      "method" -> "PayByBank",
      "paymentTime" -> "2024-01-12T11:42:00"
    ),
    Document(
      "_id" -> new ObjectId("65be3800f3a2b1c4e7d89004"),
      "amountInPence" -> 8750,
      "method" -> "Card",
      "paymentTime" -> "2024-02-03T14:00:00"
    ),
    Document(
      "_id" -> new ObjectId("65d078c0f3a2b1c4e7d89005"),
      "amountInPence" -> 300,
      "method" -> "PayByBank",
      "paymentTime" -> "2024-02-17T08:30:00"
    ),
    Document(
      "_id" -> new ObjectId("65e20780f3a2b1c4e7d89006"),
      "amountInPence" -> 12000,
      "method" -> "Card",
      "paymentTime" -> "2024-03-01T16:55:00"
    ),
    Document(
      "_id" -> new ObjectId("65fd5db0f3a2b1c4e7d89007"),
      "amountInPence" -> 650,
      "method" -> "PayByBank",
      "paymentTime" -> "2024-03-22T10:10:00"
    ),
    Document(
      "_id" -> new ObjectId("6612e2f0f3a2b1c4e7d89008"),
      "amountInPence" -> 9999,
      "method" -> "Card",
      "paymentTime" -> "2024-04-08T13:25:00"
    ),
    Document(
      "_id" -> new ObjectId("66224990f3a2b1c4e7d89009"),
      "amountInPence" -> 450,
      "method" -> "PayByBank",
      "paymentTime" -> "2024-04-19T17:05:00"
    ),
    Document(
      "_id" -> new ObjectId("663892c0f3a2b1c4e7d8900a"),
      "amountInPence" -> 3200,
      "method" -> "Card",
      "paymentTime" -> "2024-05-06T12:00:00"
    )
  )

  private def seed(): Future[Unit] =
    collection
      .countDocuments()
      .headOption()
      .flatMap:
        case Some(0) | None =>
          collection.insertMany(seedData).toFuture().map(_ => ())
        case _ => Future.successful(())

  createIndexes()
    .flatMap(_ => seed())
    .failed.foreach: e => 
    println(s"[MongoSeeder] Seeding failed: ${e.getMessage}")
