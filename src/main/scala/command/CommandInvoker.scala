package de.htwg.scm
package command

// Command invoker
class CommandInvoker {
  private var commandHistory: List[ICommand] = List.empty
  private var redoHistory: List[ICommand] = List.empty

  // Executes the given command
  def executeCommand(command: ICommand): Unit = {
    command.execute()
    commandHistory = command :: commandHistory
    redoHistory = List.empty
  }

  // Undoes the last executed command
  def undoCommand(): Unit = {
    if (commandHistory.nonEmpty) {
      val lastCommand = commandHistory.head
      commandHistory = commandHistory.tail
      redoHistory = lastCommand :: redoHistory
      lastCommand.undo()
    } else {
      println("No commands to undo.")
    }
  }

  // Redoes the last undone command
  def redoCommand(): Unit = {
    if (redoHistory.nonEmpty) {
      val lastUndoneCommand = redoHistory.head
      redoHistory = redoHistory.tail
      commandHistory = lastUndoneCommand :: commandHistory
      lastUndoneCommand.redo()
    } else {
      println("No commands to redo.")
    }
  }
}

