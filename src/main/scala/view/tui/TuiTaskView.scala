package de.htwg.scm
package view.tui

import controller.ITaskController
import model.*
import state.*

import scala.util.matching.Regex
import scala.util.{Failure, Success, Try}


class TuiTaskView(project: Project, taskController: ITaskController) extends TuiModelView[Task]("task", taskController) with IObserver {
  override protected def convertToModel(args: String): Try[Task] = Try {
    Task.apply(args).copy(project_id = project.id)
  }

  override protected def handleSet(model: Task): Try[String] = {
    if (model.id < 0) {
      // add task
      taskController.add(model)
      Success("Item added")
    } else {
      val found = controller.get(model.id)
      if (found.isDefined && found.get.project_id == project.id) {
        // update task
        taskController.update(model.id, model)
        Success("Task updated")
      } else {
        Failure(new IllegalArgumentException("Task with id " + model.id + " not found, to add a task dont use -i <id>"))
      }
    }
  }

  override protected def handleList(): Try[String] = {
    taskController.getAllByProject(project).map(_.toString).mkString("\n") match {
      case "" => Success("No tasks")
      case tasks => Success(tasks)
    }
  }

  override protected def handleHelp(): Try[String] = {
    Success("set\t\t[-i <id>] [-t <title>] [-d <description>]\t\t<-> if id is not set add a task, else update task\n" +
      "rm\t\t<id ...>\t\t<-> remove a tasks by id (whitespace separated)\n" +
      "ls\t\t<-> list tasks\n" +
      "help\t<-> show this help")
  }

  override def published(): Unit = {
    print(handleList().get)
  }
}
