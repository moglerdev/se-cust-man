package de.htwg.scm
package command

import model.Task
import state.TaskState
import model.Task

case class OpenTaskCommand(state: TaskState, task: Task) extends ICommand {
  private val prevTask: Option[Task] = state.get

  override def execute(): Boolean = {
    state.set(Some(task))
    true
  }

  override def undo(): Boolean = {
    state.set(prevTask)
    true
  }

  override def redo(): Boolean = {
    execute()
  }
}

