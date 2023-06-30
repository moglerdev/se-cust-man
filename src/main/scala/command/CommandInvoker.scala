package de.htwg.scm
package command

import de.htwg.scm.state.ClosedState
import de.htwg.scm.state.IState

trait ICommandInvoker[TModel] {
  def setState(state: IState[TModel]): Unit

  def getState: IState[TModel]

  def executeCommand(command: ICommand[TModel]): Unit

  def undoCommand(): IState[TModel]

  def redoCommand(): IState[TModel]
}

// Command invoker
class CommandInvoker[TModel] extends ICommandInvoker[TModel] {
  private var commandHistory: List[ICommand[TModel]] = List.empty
  private var redoHistory: List[ICommand[TModel]] = List.empty
  private var state: IState[TModel] = ClosedState[TModel]()

  def setState(state: IState[TModel]): Unit = {
    this.state = state
  }

  def getState: IState[TModel] = {
    state
  }

  // Executes the given command
  def executeCommand(command: ICommand[TModel]): Unit = {
    command.execute()
    commandHistory = command :: commandHistory
    redoHistory = List.empty
  }

  // Undoes the last executed command and returns the new state
  def undoCommand(): IState[TModel] = {
    if (commandHistory.nonEmpty) {
      val lastCommand = commandHistory.head
      commandHistory = commandHistory.tail
      redoHistory = lastCommand :: redoHistory
      lastCommand.undo()
      state
    } else {
      println("No commands to undo.")
      state
    }
  }

  // Redoes the last undone command and returns the new state
  def redoCommand(): IState[TModel] = {
    if (redoHistory.nonEmpty) {
      val lastUndoneCommand = redoHistory.head
      redoHistory = redoHistory.tail
      commandHistory = lastUndoneCommand :: commandHistory
      lastUndoneCommand.redo()
      state
    } else {
      println("No commands to redo.")
      state
    }
  }
}
