package de.htwg.scm
package view.tui

import com.google.inject.{Guice, Injector}
import net.codingwell.scalaguice.InjectorExtensions.*
import store.StoreModule
import controller.{ControllerModule, IProjectController, ITaskController}
import model.{Customer, Project}
import view.tui.TuiModelView

import scala.util.{Failure, Success, Try}


class TuiProjectView(project: Customer, projectController: IProjectController) extends TuiModelView[Project]("project", projectController) with IObserver {
  val injector: Injector = Guice.createInjector(new ControllerModule, new StoreModule)
  val taskController: ITaskController = injector.instance[ITaskController]

  override protected def convertToModel(args: String): Try[Project] = Try {
    Project.apply(args, project)
  }

  override protected def handleSet(model: Project): Try[String] = {
    if (model.id < 0) {
      // add task
      projectController.add(model)
      Success("Item added")
    } else {
      val found = controller.get(model.id)
      if (found.isDefined) {
        // update task
        projectController.update(model.id, model)
        Success("Project updated")
      } else {
        Failure(new IllegalArgumentException("Project with id " + model.id + " not found, to add a project dont use -i <id>"))
      }
    }
  }

  override protected def handleList(): Try[String] = {
    projectController.getAllByCustomer(project).map(_.toString).mkString("\n") match {
      case "" => Success("No projects found")
      case tasks => Success(tasks)
    }
  }

  override protected def handleHelp(): Try[String] = {
    Success("set\t\t[-i <id>] [-t <title>] [-d <description>]\t\t<-> if id is not set add a project, else update project\n" +
      "rm\t\t<id ...>\t\t<-> remove projects by id (whitespace separated)\n" +
      "ls\t\t<-> list projects\n" +
      "help\t<-> show this help")
  }

  override def handleCommand(cmd: String, args: String): Try[String] = {
    cmd match {
      case "open" =>
        Try(args.toInt) match {
          case Success(projectId) =>
            val project = projectController.get(projectId)
            if (project.isDefined) {
              setNext(new TuiTaskView(project.get, taskController))
              Success("Project opened")
            } else {
              Failure(new IllegalArgumentException("Project with id " + projectId + " not found"))
            }
          case Failure(_) =>
            Failure(new IllegalArgumentException("Invalid project ID: " + args))
        }
      case _ => super.handleCommand(cmd, args)
    }
  }

  override def published(): Unit = {
    print(handleList().get)
  }
}
