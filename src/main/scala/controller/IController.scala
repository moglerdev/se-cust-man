package de.htwg.scm
package controller

import model.*

import de.htwg.scm.store.IStore

// Trait representing a controller for managing customers
trait ICustomerController extends IModelController[Customer] {
  def getByProject(project: Project): Option[Customer]
  def filter(name: Option[String], email: Option[String], phone: Option[String]): List[Customer]
}

// Trait representing a controller for managing projects
trait IProjectController extends IModelController[Project] {
  def getAllByCustomer(customer: Customer): List[Project]
  def getByTask(task: Task): Option[Project]
  def filter(title: Option[String], description: Option[String]): List[Project]
}

// Trait representing a controller for managing tasks
trait ITaskController extends IModelController[Task] {
  def getAllByProject(project: Project): List[Task]
  def filter(title: Option[String], description: Option[String]): List[Task]
}
