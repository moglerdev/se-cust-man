package de.htwg.scm
package controller

import models._

trait IModelController[TModel] {
  def add(customer: Customer): Boolean
  def remove(customer: Customer): Boolean
  def update(customer: Customer): Boolean
  def getAll: List[TModel]
  def get(id: Int): Option[TModel]
}

trait ICustomerController extends IModelController[Customer] {
  def getProjects(customer: Customer): List[Project]
}

trait IProjectController extends IModelController[Project] {
  def getTasks(project: Project): List[Task]
}

trait ITaskController extends IModelController[Task] {
}

class CustomerController {

}
