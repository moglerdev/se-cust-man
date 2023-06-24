package de.htwg.scm
package store

import model._

trait IStore[TModel] {
  def create(model: TModel): Int
  def read(id: Int): Option[TModel]
  def update(id: Int, model: TModel): Int
  def delete(id: Int): Int
  def delete(model: TModel): Int
  def getAll: List[TModel]
  def getLastId: Int
}

trait ICustomerStore extends IStore[Customer] {

}

trait ITaskStore extends IStore[Task] {

}

trait IProjectStore extends IStore[Project] {

}
