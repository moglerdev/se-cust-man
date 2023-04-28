package de.htwg.se_cust_man.cli

import de.htwg.se_cust_man.Observer
import de.htwg.se_cust_man.cli.view.{CliView, CustomerView, ProjectView}
import de.htwg.se_cust_man.controllers.{CustomerController, EditorCustomerController, EditorProjectController, ProjectController, SessionController}
import de.htwg.se_cust_man.cli.{Cli, Input}
import de.htwg.se_cust_man.models._

import scala.io.StdIn

class MainWindow extends Observer {
  var isRunning: Boolean = true

  var customersL : Vector[Customer] = Vector.empty[Customer]
  var projectsL : Vector[Project] = Vector.empty[Project]

  val editorCustomer = new EditorCustomerController(customersL)
  val editorProjects = new EditorProjectController(projectsL)
  val customers = new CustomerController(customersL)
  val projects = new ProjectController(projectsL)

  val views : Vector[CliView] = Vector(
    new CustomerView(editorCustomer),
    new ProjectView(editorProjects)
  )

  override def onNotify(): Unit = {

  }

  def generateList(): String = {
    val cs = customers.getCustomers.map(x => s"(CUSTOMER) ${x.name} [${x.id}]")
    val ps = projects.getProjects.map(x => s"(PROJECT) ${x.title} [${x.id}]")
    (cs ++ ps).mkString("\n")
  }

  def openCustomer(id: Int) : Option[String] = {
    if (editorCustomer.openCustomer(id).isDefined) {
      Some(s"You open customer ${id}")
    } else {
      Some(s"Customer was not found, you can create a new one with: create customer <name>.")
    }
  }

  def openProject(id: Int) : Option[String] = {
    if (editorProjects.openProject(id = id).isDefined) {
      Some(s"You open project ${id}")
    } else {
      Some(s"Project was not found, you can create a new one with: create project <title>.")
    }
  }

  def open(input: Input): Option[String] = {
    val idTry = scala.util.Try(input.arguments.tail.head.toInt)
    if (idTry.isFailure) return Some("Wrong Arguments for open: open <customer | project> <id>")
    if (input.arguments.length > 2) return Some("You need to specify a what you want to open and the id of it")
    input.arguments.head match {
      case "customer" => openCustomer(idTry.get)
      case "project" => openProject(idTry.get)
      case _ => Some("You need to specify a what you want to open and the id of it")
    }
  }

  def create(input: Input): Option[String] = {
    if (input.arguments.isEmpty) return Some("You can't create a customer with a name!")
    input.arguments.head match
      case "customer" =>
        editorCustomer.createCustomer()
        Some("New Customer")
      case "project" =>
        editorProjects.createProject()
        Some("New Project")
  }

  def eval(input: Input): Option[String] = {
    input.command match
      case "connect" => if (Cli.startSocket()) Some("Connected") else Some("Connection failed")
      case "exit" =>
        isRunning = false
        Some("Bye!")
      case "ls" => Some(generateList())
      case "create" => create(input)
      case "open" => open(input)
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
