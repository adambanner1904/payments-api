package modules

import com.google.inject.{AbstractModule, Provides, Singleton}
import org.mongodb.scala.* 
import play.api.Configuration

class MongoModule extends AbstractModule: 

  override def configure(): Unit = 
    bind(classOf[MongoSeeder]).asEagerSingleton()

  @Provides @Singleton
  def provideMongoDatabase(config: Configuration): MongoDatabase = 
    val uri = config.get[String]("mongodb.uri")
    val dbName = config.get[String]("mongodb.database")
    MongoClient(uri).getDatabase(dbName)