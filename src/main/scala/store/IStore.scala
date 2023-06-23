package de.htwg.scm
package store

trait IStore[TModel] {
  def create(model: TModel): Int
  def read(id: Int): Option[TModel]
  def update(id: Int, model: TModel): Int
  def delete(id: Int): Int
  def getAll: List[TModel]
  def getLastId: Int
}
