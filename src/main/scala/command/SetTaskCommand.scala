package de.htwg.scm
package command

import model.Task
import state.TaskState
import model.Task

case class SetTaskCommand(state: TaskState, project_id: Int, title: Option[String], description: Option[String]) extends ICommand {
  val task: Option[Task] = state.option

  override def execute(): Boolean = {
    val c = task.getOrElse(Task(-1, -1, "", ""))
    val toSet = Task(
      c.id,
      project_id,
      title.getOrElse(c.title),
      description.getOrElse(c.description),
    )
    state.set(Some(toSet))
    true
  }

  override def undo(): Boolean = {
    state.set(task)
    true
  }

  override def redo(): Boolean = {
    execute()
  }
}

