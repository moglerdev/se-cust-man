package de.htwg.scm
package command

import model.Task
import state.TaskState
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class SetTaskCommandSpec extends AnyWordSpec with Matchers {
  "A SetTaskCommand" when {
    val initialState: Option[Task] = Some(Task(1, 1, "Task A", "Description A"))
    val state: TaskState = new TaskState()
    state.set(initialState)
    val command: SetTaskCommand = SetTaskCommand(state, 2, Some("Task B"), None)

    "executed" should {
      "set the specified task properties" in {
        command.execute() should be(true)
        state.get should be(Task(1, 2, "Task B", "Description A"))
      }
    }

    "undone" should {
      "revert the task state to its original value" in {
        command.undo() should be(true)
        state.get should be(initialState.get)
      }
    }

    "redone" should {
      "execute the command again and set the specified task properties" in {
        command.redo() should be(true)
        state.get should be(Task(1, 2, "Task B", "Description A"))
      }
    }
  }
}
