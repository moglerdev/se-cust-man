package de.htwg.scm
package command

import model.Project
import state.ProjectState
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class SetProjectCommandSpec extends AnyWordSpec with Matchers {
  "A SetProjectCommand" when {
    val initialState: Option[Project] = Some(Project(1, 1, "Project A", "Description A"))
    val state: ProjectState = new ProjectState()
    state.set(initialState)
    val command: SetProjectCommand = SetProjectCommand(state, 2, Some("Project B"), None)

    "executed" should {
      "set the specified project properties" in {
        command.execute() should be(true)
        state.get should be(Project(1, 2, "Project B", "Description A"))
      }
    }

    "undone" should {
      "revert the project state to its original value" in {
        command.undo() should be(true)
        state.get should be(initialState.get)
      }
    }

    "redone" should {
      "execute the command again and set the specified project properties" in {
        command.redo() should be(true)
        state.get should be(Project(1, 2, "Project B", "Description A"))
      }
    }
  }
}
