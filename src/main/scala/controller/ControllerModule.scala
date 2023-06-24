package de.htwg.scm
package controller

import com.google.inject.AbstractModule
import net.codingwell.scalaguice.ScalaModule

class ControllerModule extends AbstractModule with ScalaModule {
  override def configure(): Unit = {
    bind[ICustomerController].to[CustomerController].asEagerSingleton()
    bind[IProjectController].to[ProjectController].asEagerSingleton()
    bind[ITaskController].to[TaskController].asEagerSingleton()
  }
}
