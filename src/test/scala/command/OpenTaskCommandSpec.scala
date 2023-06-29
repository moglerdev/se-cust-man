package de.htwg.scm
package command

import org.scalatestplus.mockito.MockitoSugar
import org.mockito.Mockito._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import model.Task
import state.TaskState


class OpenTaskCommandSpec extends AnyWordSpec with Matchers with MockitoSugar {

  "An OpenTaskCommand" when {
    val initialState: Option[Task] = None
    val newState: Option[Task] = Some(Task(1, 1, "Task 1", "Description"))

    val mockState: TaskState = mock[TaskState]
    when(mockState.option).thenReturn(initialState)

    val command: OpenTaskCommand = OpenTaskCommand(mockState, newState.get)

    "executed" should {
      "set the state to the new task" in {
        command.execute() shouldBe true
        verify(mockState).set(newState)
      }
    }

    "undone" should {
      "restore the state to the previous task" in {
        command.undo() shouldBe true
        verify(mockState).set(initialState)
      }
    }

    "redone" should {
      "set the state back to the new task" in {
        command.redo() shouldBe true
        verify(mockState, times(2)).set(newState)
      }
    }
  }
}
