package de.htwg.scm
package controller

import model.*

import de.htwg.scm.store.IStore


trait ICustomerController extends IModelController[Customer] {
  def getByProject(project: Project): Option[Customer]
  def filter(name: Option[String], email: Option[String], phone: Option[String]): List[Customer]
}

trait IProjectController extends IModelController[Project] {
  def getAllByCustomer(customer: Customer): List[Project]
  def getByTask(task: Task): Option[Project]
  def filter(title: Option[String], description: Option[String]): List[Project]
}

trait ITaskController extends IModelController[Task] {
  def getAllByProject(project: Project): List[Task]
  def filter(title: Option[String], description: Option[String]): List[Task]
}

