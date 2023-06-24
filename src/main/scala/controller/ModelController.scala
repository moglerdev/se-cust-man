package de.htwg.scm
package controller

import store.IStore

trait IModelController[TModel] extends IPublisher {
  def add(model: TModel): Boolean
  def remove(model: TModel): Boolean
  def update(id: Int, model: TModel): Boolean
  def getAll: List[TModel]
  def get(id: Int): Option[TModel]
}

abstract class ModelController[TModel] (store: IStore[TModel]) extends Publisher with IModelController[TModel] {
  def add(model: TModel): Boolean = {
    val result = store.create(model) > 0
    if (result) publish()
    result
  }

  def remove(model: TModel): Boolean = {
    val result = store.delete(model) > 0
    if (result) publish()
    result
  }
  def update(id: Int, model: TModel): Boolean = {
    val result = store.update(id, model) > 0
    if (result) publish()
    result
  }
  def getAll: List[TModel] = store.getAll
  def get(id: Int): Option[TModel] = store.read(id)
}
