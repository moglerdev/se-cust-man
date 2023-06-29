package de.htwg.scm
package command

import model.Project
import state.ProjectState

case class SetProjectCommand(state: ProjectState, customer_id: Int, title: Option[String], description: Option[String]) extends ICommand {
  val project: Option[Project] = state.option

  override def execute(): Boolean = {
    val c = project.getOrElse(Project(-1, -1, "", ""))
    val toSet = Project(
      c.id,
      customer_id,
      title.getOrElse(c.title),
      description.getOrElse(c.description),
    )
    state.set(Some(toSet))
    true
  }

  override def undo(): Boolean = {
    state.set(project)
    true
  }

  override def redo(): Boolean = {
    execute()
  }
}
