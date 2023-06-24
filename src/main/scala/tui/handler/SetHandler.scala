package de.htwg.scm
package tui.handler

import state.{CustomerState, IState, ProjectState, TaskState}
import model.{Customer, Project, Task}
import command.{ICommand, SetCustomerCommand, SetProjectCommand, SetTaskCommand}
import store.ICustomerStore

import scala.util.Try
import scala.util.matching.Regex

abstract class SetHandler extends CommandHandler {
  def commandPattern: Regex //"""customer\s+set\s+(\d+)\s+(.+)""".r
  def fieldPattern: Regex //"""<(\w+)=([^;>]+)""".r

  def createSetCommand(id: Int, fields: Map[String, String]): ICommand

  override def handle(command: String): Try[ICommand] = {
    val cp = commandPattern

    command match {
      case cp(id, fields) =>
        val fp = fieldPattern
        val fieldMap = collection.mutable.Map[String, String]()
        fp.findAllMatchIn(fields).foreach { m =>
          val key = m.group(1)
          val value = m.group(2)
          fieldMap += key -> value
        }
        Try {
          createSetCommand(id.toInt, fieldMap.toMap)
        }
      case _ =>
        super.handle(command)
    }
  }
}

class SetCustomerHandler(state: CustomerState) extends SetHandler {
  override def commandPattern: Regex = """customer\s+set\s+(\d+)\s+(.+)""".r
  override def fieldPattern: Regex = """<(\w+)=([^;>]+)""".r


  // Helper method to create a SetCustomerCommand
  override def createSetCommand(id: Int, fields: Map[String, String]): ICommand = {
    val name = fields.get("name")
    val email = fields.get("email")
    val phone = fields.get("phone")
    val address = fields.get("address")

    SetCustomerCommand(state, name, email, phone, address)
  }
}

class SetProjectHandler(state: ProjectState, customer: Customer) extends SetHandler {
  override def commandPattern: Regex = """project\s+set\s+(\d+)\s+(.+)""".r
  override def fieldPattern: Regex = """<(\w+)=([^;>]+)""".r

  // Helper method to create a SetProjectCommand
  override def createSetCommand(id: Int, fields: Map[String, String]): ICommand = {
    val title = fields.get("title")
    val description = fields.get("description")

    SetProjectCommand(state, customer.id, title, description)
  }
}

class SetTaskHandler(state: TaskState, project: Project) extends SetHandler {
  override def commandPattern: Regex = """task\s+set\s+(\d+)\s+(.+)""".r
  override def fieldPattern: Regex = """<(\w+)=([^;>]+)""".r

  // Helper method to create a SetProjectCommand
  override def createSetCommand(id: Int, fields: Map[String, String]): ICommand = {
    val title = fields.get("title")
    val description = fields.get("description")

    SetTaskCommand(state, project.id, title, description)
  }
}

