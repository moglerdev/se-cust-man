package de.htwg.scm
package command

import de.htwg.scm.state.IState

import scala.util.Try

// Command for quitting the TUI
class QuitCommand extends ICommand {
  override def execute(): Boolean = {
    // Perform any necessary cleanup or finalization tasks
    println("Bye!")
    // Additional cleanup code if needed
    sys.exit()
    true
  }

  override def undo(): Boolean = {
    // This command does not support undo operation
    false
  }

  override def redo(): Boolean = {
    // This command does not support redo operation
    false
  }
}
