package de.htwg.scm
package command

import model.{Project, Task}
import state.{ProjectState, TaskState}

case class OpenProjectCommand(state: ProjectState, project: Project) extends ICommand {
  private val prevProject: Option[Project] = state.option

  override def execute(): Boolean = {
    state.set(Some(project))
    true
  }

  override def undo(): Boolean = {
    state.set(prevProject)
    true
  }

  override def redo(): Boolean = {
    execute()
  }
}
