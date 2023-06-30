package de.htwg.scm
package command

import state.IState

import scala.util.Try

// Trait representing a command
trait ICommand[TModel] {
  // Executes the command and returns a boolean indicating success or failure
  def execute(): IState[TModel]

  // Undoes the command and returns a boolean indicating success or failure
  def undo(): IState[TModel]

  // Redoes the command and returns a boolean indicating success or failure
  def redo(): IState[TModel]
}

