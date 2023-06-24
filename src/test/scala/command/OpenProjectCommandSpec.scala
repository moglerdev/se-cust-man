package de.htwg.scm
package command

import org.scalatestplus.mockito.MockitoSugar
import org.mockito.Mockito._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import model.Project
import state.ProjectState

class OpenProjectCommandSpec extends AnyWordSpec with Matchers with MockitoSugar {

  "An OpenProjectCommand" when {
    val initialState: Option[Project] = None
    val newState: Option[Project] = Some(Project(1, 1, "Project 1", "Description"))

    val mockState: ProjectState = mock[ProjectState]
    when(mockState.option).thenReturn(initialState)

    val command: OpenProjectCommand = OpenProjectCommand(mockState, newState.get)

    "executed" should {
      "set the state to the new project" in {
        command.execute() shouldBe true
        verify(mockState).set(newState)
      }
    }

    "undone" should {
      "restore the state to the previous project" in {
        command.undo() shouldBe true
        verify(mockState).set(initialState)
      }
    }

    "redone" should {
      "set the state back to the new project" in {
        command.redo() shouldBe true
        verify(mockState, times(2)).set(newState)
      }
    }
  }
}
