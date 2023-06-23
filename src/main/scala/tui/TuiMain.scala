package de.htwg.scm
package tui

import controller.{EditorController, SessionController, Subscriber}

import com.google.inject.Guice
import models.{Customer, Project, Task}

import net.codingwell.scalaguice.InjectorExtensions.*
import de.htwg.scm.ScmModule
import de.htwg.scm.store.{DBCustomerStore, IStore, DBProjectStore}
import de.htwg.scm.tui.handler.{AuthHandler, CloseHandler, CustomerHandler, IHandler, Request}

import scala.io.StdIn


@main def run(): Unit = {
  val injector = Guice.createInjector(new ScmModule)
  val cusStr = injector.instance[IStore[Customer]]
  val prjStr = injector.instance[IStore[Project]]
  val tskStr = injector.instance[IStore[Task]]

  val customer = new EditorController[Customer](cusStr)
  val project = new EditorController[Project](prjStr)
  val task = new EditorController[Task](tskStr)
  val session = new SessionController()

  val h1 = new CloseHandler()
  h1.setNext(new AuthHandler(session))
    .setNext(new CustomerHandler(customer))

  println("Welcome to Se-Cust-Man")
  var running = true
  while (running) {
    val read = StdIn.readLine("prompt> ")
    val ret = h1.handle(Request(read))
    if (ret == ".exit") running = false
    else println(ret)
  }
  println("Bye!")

}
