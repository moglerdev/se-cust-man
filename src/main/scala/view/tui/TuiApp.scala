package de.htwg.scm
package view.tui

import com.google.inject.Guice
import controller.*
import model.Project
import store.*
import net.codingwell.scalaguice.InjectorExtensions.*
import view.tui.TuiCustomerView

import scala.io.StdIn
import scala.util.{Failure, Success}

object TuiApp {
  def buildViews(): ITuiView = {
    val injector = Guice.createInjector(new ControllerModule, new StoreModule)
    val customerController: ICustomerController = injector.instance[ICustomerController]
    val view = new TuiCustomerView(customerController)
    view
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
