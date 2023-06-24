package de.htwg.scm
package command

// Command invoker
class CommandInvoker {
  private var commandHistory: List[ICommand] = List.empty
  private var redoHistory: List[ICommand] = List.empty

  def executeCommand(command: ICommand): Unit = {
    command.execute()
    commandHistory = command :: commandHistory
    redoHistory = List.empty
  }

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
