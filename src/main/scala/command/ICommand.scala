package de.htwg.scm
package command

import state.IState

import scala.util.Try

trait ICommand {
  def execute(): Boolean
  def undo(): Boolean
  def redo(): Boolean
}

