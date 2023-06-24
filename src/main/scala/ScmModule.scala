package de.htwg.scm

import com.google.inject.{AbstractModule, Key, TypeLiteral}
import net.codingwell.scalaguice.ScalaModule
import store.{DBCustomerStore, DBProjectStore, DBTaskStore, IStore, JSONCustomerStore, XMLCustomerStore, XMLProjectStore, XMLTaskStore}
import models.{Customer, Project, Task}

import de.htwg.scm.controller.EditorController
import scalafx.scene.input.KeyCode.D

class ScmModule extends AbstractModule with ScalaModule {
  override def configure(): Unit = {
    bind[IStore[Customer]].to[XMLCustomerStore]
    bind[IStore[Project]].to[XMLProjectStore]
    bind[IStore[Task]].to[XMLTaskStore]
  }
}
