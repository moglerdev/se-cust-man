package de.htwg.scm
package controller

import store._

import com.google.inject.{Guice, Injector}
import de.htwg.scm.model.{Customer, Project, Task}
import net.codingwell.scalaguice.InjectorExtensions.*

// Controller for managing projects
class ProjectController(store: IProjectStore) extends ModelController[Project](store) with IProjectController {

  // Retrieves all projects associated with a customer
  override def getAllByCustomer(customer: Customer): List[Project] = {
    store.getAll.filter(_.customer_id == customer.id)
  }

  // Retrieves the project associated with a task
  override def getByTask(task: Task): Option[Project] = {
    store.getAll.find(_.id == task.project_id)
  }

  // Filters projects based on title and description criteria
  override def filter(title: Option[String], description: Option[String]): List[Project] = {
    store.getAll.filter(p => {
      title match {
        case Some(t) => p.title.contains(t)
        case None => true
      }
    }).filter(p => {
      description match {
        case Some(d) => p.description.contains(d)
        case None => true
      }
    })
  }
}
