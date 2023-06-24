package de.htwg.scm
package command

import state.IState

import scala.util.Try

// Trait representing a command
trait ICommand {
  // Executes the command and returns a boolean indicating success or failure
  def execute(): Boolean

  // Undoes the command and returns a boolean indicating success or failure
  def undo(): Boolean

  // Redoes the command and returns a boolean indicating success or failure
  def redo(): Boolean
}

