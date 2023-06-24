package de.htwg.scm
package tui

import com.google.inject.Guice
import de.htwg.scm.controller.*
import de.htwg.scm.store.*
import de.htwg.scm.tui.view.{ITuiView, TuiTaskView}
import net.codingwell.scalaguice.InjectorExtensions.*

import scala.io.StdIn
import scala.util.{Failure, Success}

object TuiApp {
  def buildViews(): ITuiView = {
    val injector = Guice.createInjector(new ControllerModule, new StoreModule)
    val customerController: ICustomerController = injector.instance[ICustomerController]
    val projectController: IProjectController = injector.instance[IProjectController]
    val taskController: ITaskController = injector.instance[ITaskController]

//    val customerView = new CustomerView(customerController)
//    val projectView = new ProjectView(projectController)
    val task = new TuiTaskView(taskController)
    task
  }
  def start(): Unit = {

    val views = buildViews()
    var running = true
    while (running) {
      StdIn.readLine("prompt> ") match {
        case "exit" => running = false
        case input => views.handle(input) match
          case Success(msg) => println(msg)
          case Failure(exception) => println(s"[ERROR] ${exception.getMessage}")
      }
    }
  }

}
