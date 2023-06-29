package de.htwg.scm
package store

import model.{Customer, Project, Task}
import store.*

import com.google.inject.{AbstractModule, Key, TypeLiteral}
import net.codingwell.scalaguice.ScalaModule
import scalafx.scene.input.KeyCode.D

class StoreModule extends AbstractModule with ScalaModule {

  override def configure(): Unit = {
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
