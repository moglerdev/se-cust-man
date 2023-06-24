package de.htwg.scm
package tui.view

import model.*
import state.*

import de.htwg.scm.controller.ITaskController

import scala.util.{Failure, Success, Try}
import scala.util.matching.Regex


class TuiTaskView(taskController: ITaskController) extends TuiModelView("task") with IObserver {
  taskController.observe(this)

  private def addTask(args: String): Try[String] = Try {
    val task = Task.apply(args)
    taskController.add(task)
    "Task added"
  }

  private def removeTask(args: String): Try[String] = {
    val regex: Regex = """(\d+)""".r
    args match {
      case regex(id) => {
        val task = taskController.get(id.toInt)
        if (task.isEmpty) {
          return Failure(new IllegalArgumentException("Task with id " + args + " not found"))
        }
        taskController.remove(task.get)
        Success("Task removed")
      }
      case _ => Failure(new IllegalArgumentException("Wrong arguments for remove command"))
    }
  }

  private def listTasks(args: String): Try[String] = {
    taskController.getAll.map(_.toString).mkString("\n") match {
      case "" => Success("No tasks")
      case tasks => Success(tasks)
    }
  }

  private def help(): Try[String] = {
    Success("add <task> - add a task\n" +
      "remove <id> - remove a task\n" +
      "list <id> - list tasks\n" +
      "help - show this help")
  }

  private def setTask(args: String): Try[String] = Try {
//    val task = Task.apply(args)
//    taskController.update(task)
    "Task set"
  }

  override def handleCommand(command: String, args: String): Try[String] = {
    command match {
      case "add" => addTask(args)
      case "set" => addTask(args)
      case "remove" => removeTask(args)
      case "list" => listTasks(args)
      case "help" => help()
      case _ => Failure(new IllegalArgumentException("Unknown argument: " + command))
    }
  }

  override def dispose(): Unit = {
    taskController.unobserve(this)
  }

  override def published(): Unit = {
    print("UPDATED")
  }
}
