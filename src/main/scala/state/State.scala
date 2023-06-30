package de.htwg.scm
package state

import scala.util._


trait IState[TModel] {
  def isOpen: Boolean
  def isClosed: Boolean
  def isModified: Boolean
  def open(state: TModel): IState[TModel]
  def close: IState[TModel]
  def set(model: TModel): Try[IState[TModel]]
  def get: Option[TModel]
}


abstract class State[TModel](val _state: Option[TModel]) extends IState[TModel] {
  override def isOpen: Boolean = _state.isDefined
  override def isClosed: Boolean = _state.isEmpty
  override def open(model: TModel): IState[TModel] = new OpenState[TModel](_state.get)
}

class ClosedState[TModel] extends State[TModel](None) {
  override def close: IState[TModel] = this
  override def set(model: TModel): Try[IState[TModel]] = Failure(new Exception("State is closed"))
  override def get: Option[TModel] = None
  override def isModified: Boolean = false
}

class OpenState[TModel](val state: TModel) extends State[TModel](Some(state)) {
  override def close: IState[TModel] = new ClosedState[TModel]()
  override def set(model: TModel): Try[IState[TModel]] = Success(new ModifiedState[TModel](model))
  override def get: Option[TModel] = _state
  override def isModified: Boolean = false
}

class ModifiedState[TModel](val state: TModel) extends State[TModel](Some(state)) {
  override def close: IState[TModel] = new ClosedState[TModel]()
  override def set(model: TModel): Try[IState[TModel]] = Success(new OpenState[TModel](model))
  override def get: Option[TModel] = _state
  override def isModified: Boolean = true
}
