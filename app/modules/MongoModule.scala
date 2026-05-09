package modules

import com.google.inject.{AbstractModule, Provides, Singleton}
import org.mongodb.scala.* 
import play.api.Configuration
import play.api.inject.ApplicationLifecycle
import scala.concurrent.Future

class MongoModule extends AbstractModule: 

  override def configure(): Unit = 
    bind(classOf[MongoSeeder]).asEagerSingleton()

  @Provides @Singleton
  def provideMongoDatabase(config: Configuration, lifecycle: ApplicationLifecycle): MongoDatabase = 
    val uri = config.get[String]("mongodb.uri")
    val client = MongoClient(uri)
    lifecycle.addStopHook(() => Future.successful(client.close()))
    
    val dbName = config.get[String]("mongodb.database")
    client.getDatabase(dbName)