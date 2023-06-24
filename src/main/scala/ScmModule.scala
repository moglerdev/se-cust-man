package de.htwg.scm

import com.google.inject.{AbstractModule, Key, TypeLiteral}
import net.codingwell.scalaguice.ScalaModule
import store._
import model.{Customer, Project, Task}

import de.htwg.scm.controller.EditorController
import scalafx.scene.input.KeyCode.D

class ScmModule extends AbstractModule with ScalaModule {

  override def configure(): Unit = {
    bind[IStore[Customer]].to[XMLCustomerStore]
    bind[IStore[Project]].to[XMLProjectStore]
    bind[IStore[Task]].to[XMLTaskStore]

    bind[ICustomerStore].to[DBCustomerStore]
    bind[IProjectStore].to[DBProjectStore]
    bind[ITaskStore].to[DBTaskStore]

    bind[ICustomerStore].annotatedWithName("json").to[JSONCustomerStore]
    bind[IProjectStore].annotatedWithName("json").to[JSONProjectStore]
    bind[ITaskStore].annotatedWithName("json").to[JSONTaskStore]

    bind[ICustomerStore].annotatedWithName("xml").to[XMLCustomerStore]
    bind[IProjectStore].annotatedWithName("xml").to[XMLProjectStore]
    bind[ITaskStore].annotatedWithName("xml").to[XMLTaskStore]

    bind[ICustomerStore].annotatedWithName("sql").to[DBCustomerStore]
    bind[IProjectStore].annotatedWithName("sql").to[DBProjectStore]
    bind[ITaskStore].annotatedWithName("sql").to[DBTaskStore]
  }
}
