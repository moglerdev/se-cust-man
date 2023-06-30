package de.htwg.scm
package store

import model.{Customer, Project, Task}
import store.*

import com.google.inject.{AbstractModule, Key, TypeLiteral}
import net.codingwell.scalaguice.ScalaModule
import scalafx.scene.input.KeyCode.D
import java.sql.Connection
import java.sql.DriverManager
import io.github.cdimascio.dotenv.Dotenv

class StoreModule extends AbstractModule with ScalaModule {


  def url: String = {
    val dotenv: Dotenv = Dotenv.load()

    val host = dotenv.get("POSTGRES_HOST")
    val port = dotenv.get("POSTGRES_PORT")
    val database = dotenv.get("POSTGRES_DB")
    val username = dotenv.get("POSTGRES_USER")
    val password = dotenv.get("POSTGRES_PASSWORD")
    s"jdbc:postgresql://$host:$port/$database?&user=$username&password=$password"
  }

  def getConnection = {
    Class.forName("org.postgresql.Driver")
    DriverManager.getConnection(url)
  }


  override def configure(): Unit = {
    bind[Connection].toInstance(getConnection)

    bind[ICustomerStore].to[DBCustomerStore].asEagerSingleton()
    bind[IProjectStore].to[DBProjectStore].asEagerSingleton()
    bind[ITaskStore].to[DBTaskStore].asEagerSingleton()

    bind[ICustomerStore].annotatedWithName("json").to[JSONCustomerStore]
    bind[IProjectStore].annotatedWithName("json").to[JSONProjectStore]
    bind[ITaskStore].annotatedWithName("json").to[JSONTaskStore]

    bind[ICustomerStore].annotatedWithName("xml").to[XMLCustomerStore]
    bind[IProjectStore].annotatedWithName("xml").to[XMLProjectStore]
    bind[ITaskStore].annotatedWithName("xml").to[XMLTaskStore]

    bind[ICustomerStore].annotatedWithName("sql").to[DBCustomerStore].asEagerSingleton()
    bind[IProjectStore].annotatedWithName("sql").to[DBProjectStore].asEagerSingleton()
    bind[ITaskStore].annotatedWithName("sql").to[DBTaskStore].asEagerSingleton()
  }
}
