package de.htwg.scm
package controller

import model.{Customer, Project, Task}

import net.codingwell.scalaguice.InjectorExtensions.*
import com.google.inject.{Guice, Inject}
import store.*


trait ITaskController extends IModelController[Task] {
  def getAllByProject(project: Project): List[Task]
  def filter(title: Option[String], description: Option[String]): List[Task]
}


class TaskController @Inject()(store: ITaskStore) extends ModelController[Task](store) with ITaskController {
  override def getAllByProject(project: Project): List[Task] = {
    store.getAll.filter(task => task.project_id == project.id)
  }

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
