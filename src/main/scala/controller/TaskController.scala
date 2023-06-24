package de.htwg.scm
package controller

import model.{Customer, Project, Task}

import net.codingwell.scalaguice.InjectorExtensions.*
import com.google.inject.Guice
import store._

// Controller for managing tasks
class TaskController(store: ITaskStore) extends ModelController[Task](store) with ITaskController {

  // Retrieves all tasks associated with a project
  override def getAllByProject(project: Project): List[Task] = {
    store.getAll.filter(task => task.project_id == project.id)
  }

  // Filters tasks based on title and description criteria
  override def filter(title: Option[String], description: Option[String]): List[Task] = {
    store.getAll.filter(task => {
      title match {
        case Some(t) => task.title.contains(t)
        case None => true
      }
    }).filter(task => {
      description match {
        case Some(d) => task.description.contains(d)
        case None => true
      }
    })
  }
}
