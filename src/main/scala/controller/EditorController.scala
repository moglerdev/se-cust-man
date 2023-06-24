package de.htwg.scm
package controller

import com.google.inject.{Guice, Inject, Key, TypeLiteral}
import net.codingwell.scalaguice.InjectorExtensions._
import store.IStore

import de.htwg.scm.ScmModule

import scala.reflect.ClassTag

// Trait representing an editor controller for a specific model type
sealed trait IEditorController[TModel] {
  def create(model: TModel): Boolean
  def open(id: Int): Boolean
  def update(model: TModel): Boolean
  def get: TModel
  def close: Boolean
  def isOpen: Boolean
  def save: Boolean
}

// Controller for managing an editor for a specific model type
class EditorController[TModel] @Inject() (_store: IStore[TModel]) extends Publisher with IEditorController[TModel] {
  private var _model: Option[TModel] = None
  private var _isNew: Boolean = false
  private var _isDirty: Boolean = false

  // Creates a new model instance and marks it as dirty
  override def create(model: TModel): Boolean = {
    _model = Some(model)
    _isDirty = true
    publish()
    true
  }

  // Opens the editor with the specified ID
  override def open(id: Int): Boolean = {
    _model = Some(null.asInstanceOf[TModel])
    _isDirty = false
    publish()
    true
  }

  // Updates the model instance and marks it as dirty
  override def update(model: TModel): Boolean = {
    _model = Some(model)
    _isDirty = true
    publish()
    true
  }

  // Returns the current model instance
  override def get: TModel = _model.get

  // Closes the editor and clears the model instance
  override def close: Boolean = {
    _model = None
    publish()
    true
  }

  // Checks if the editor is currently open
  override def isOpen: Boolean = _model.isDefined

  // Saves the model instance to the store
  override def save: Boolean = {
    _model match {
      case Some(model) =>
        var res = false
        if (_isNew) {
          res = _store.create(model) > 0
        } else {
          res = _store.update(0, model) > 0
        }
        if (res) {
          _isNew = false
          _isDirty = false
          publish()
        }
        res
      case None => false
    }
  }
}
