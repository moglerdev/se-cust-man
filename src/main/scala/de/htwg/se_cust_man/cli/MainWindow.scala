package de.htwg.se_cust_man.cli

import de.htwg.se_cust_man.Observer
import de.htwg.se_cust_man.cli.view.{CliView, CustomerView}
import de.htwg.se_cust_man.controllers.{CustomerController, EditorCustomerController, SessionController}
import de.htwg.se_cust_man.cli.{Cli, Input}

import scala.io.StdIn

class MainWindow extends Observer {
  private var isRunning: Boolean = true

  private val editorCustomer = new EditorCustomerController
  private val customers = new CustomerController

  private val views : Vector[CliView] = Vector(
    new CustomerView(editorCustomer)
  )

  override def onNotify(): Unit = {

  }

  private def listCustomer(): String = {
    customers.getCustomers().map(x => s"(CUSTOMER) ${x.name} [${x.id}]").mkString("\n")
  }

  private def eval(input: Input): Option[String] = {
    input.command match
      case "exit" =>
        isRunning = false
        Some("Bye!")
      case "ls" => Some(listCustomer())
      case "create" => {
        editorCustomer.createCustomer()
        Some("New Customer")
      }
      case "open" => {
        if (input.arguments.isEmpty) return Some("You need to specify a customer you want to open")
        val idTry = scala.util.Try(input.arguments.head.toInt)
        if (idTry.isFailure) return Some("You have to give a number!")
        if(editorCustomer.openCustomer(id = idTry.get).isDefined){
          Some(s"You open customer ${idTry.get}")
        }else {
          Some(s"Customer was not found, you can create a new one.")
        }
      }
      case _ => None
  }

  def loop(): Unit = {
    while(isRunning) {
      val view = views.find(x => x.isOpen)
      var next = true
      val prefix = if (view.isEmpty) "(USER)" else view.get.promptPrefix
      val input = Cli.prompt(prefix)
      if (view.isDefined) {
        val res = view.get.eval(input)
        next = res.isEmpty
        if (res.isDefined) {
          println(res.get)
        }
      }
      if (next) {
        println(this.eval(input).getOrElse("Unknown Command"))
      }
    }


    views.foreach(x => x.close())
  }
}
