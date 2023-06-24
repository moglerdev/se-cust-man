package de.htwg.scm
package state

import scala.util.Try

trait IState[TModel] {
  def set(model: Option[TModel]): IState[TModel]
  def get: TModel
  def option: Option[TModel]
  def isDefined: Boolean
  def isEmpty: Boolean
}
