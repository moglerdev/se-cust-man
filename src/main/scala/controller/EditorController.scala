package de.htwg.scm
package controller

import com.google.inject.{Guice, Inject, Key, TypeLiteral}
import net.codingwell.scalaguice.InjectorExtensions._
import store.IStore

import de.htwg.scm.ScmModule

import scala.reflect.ClassTag

sealed trait IEditorController[TModel] {
  def create(model: TModel): Boolean
  def open(id: Int): Boolean
  def update(model: TModel) : Boolean
  def get: TModel
  def close: Boolean
  def isOpen: Boolean
  def save: Boolean
  def undo: Boolean
  def redo: Boolean
}

trait EditorCommand

case class OpenCommand[TModel](prev: TModel, model: TModel) extends EditorCommand
case class UpdateCommand[TModel](prev: TModel, model: TModel) extends EditorCommand

class EditorController[TModel] @Inject() (_store: IStore[TModel]) extends Publisher with IEditorController[TModel]  {
  private var _model: Option[TModel] = None
  private var _isNew: Boolean = false
  private var _isDirty: Boolean = false

  private var _commands: List[EditorCommand] = List.empty

  override def create(model: TModel): Boolean = {
    _model = Some(model)
    _isDirty = true
    notifySubscribers()
    true
  }

  override def open(id: Int): Boolean = {
    _model = Some(null.asInstanceOf[TModel])
    _isDirty = false
    notifySubscribers()
    true
  }

  override def update(model: TModel): Boolean = {
    _model = Some(model)
    _isDirty = true
    notifySubscribers()
    true
  }

  override def get: TModel = _model.get

  override def close: Boolean = {
    _model = None
    notifySubscribers()
    true
  }

  override def isOpen: Boolean = _model.isDefined

  override def save: Boolean = {
    _model match {
      case Some(model) => {
        var res = false
        if (_isNew) {
          res = _store.create(model) > 0
        } else {
          res = _store.update(0, model) > 0
        }
        if (res) {
          _isNew = false
          _isDirty = false
          notifySubscribers()
        }
        res
      }
      case None => false
    }
  }

  override def undo: Boolean = {
    throw new NotImplementedError()
  }

  override def redo: Boolean = {
    throw new NotImplementedError()
  }
}
