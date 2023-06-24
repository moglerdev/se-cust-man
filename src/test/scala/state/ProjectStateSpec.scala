package de.htwg.scm
package state

import model.{Customer, Project}

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.WordSpec

class ProjectStateSpec extends WordSpec with Matchers {

  "ProjectState" should {

    "set and retrieve the project" in {
      // Create a project
      val project = Project(1, 1, "Project 1", "Description")

      // Create a project state
      val state = new ProjectState()

      // Set the project
      state.set(Some(project))

      // Retrieve the project
      val retrievedProject = state.get

      // Verify that the retrieved project matches the original project
      retrievedProject should be(project)
    }

    "return the project option" in {
      // Create a project
      val project = Project(1, 1, "Project 1", "Description")

      // Create a project state
      val state = new ProjectState()

      // Set the project
      state.set(Some(project))

      // Retrieve the project option
      val projectOption = state.option

      // Verify that the project option is Some(project)
      projectOption should be(Some(project))
    }

    "return if the project state is defined" in {
      // Create a project state without a project
      val state1 = new ProjectState()

      // Verify that the state is not defined
      state1.isDefined should be(false)

      // Create a project
      val project = Project(1, 1, "Project 1", "Description")

      // Create a project state with a project
      val state2 = new ProjectState().set(Some(project))

      // Verify that the state is defined
      state2.isDefined should be(true)
    }

    "return if the project state is empty" in {
      // Create a project state without a project
      val state1 = new ProjectState()

      // Verify that the state is empty
      state1.isEmpty should be(true)

      // Create a project
      val project = Project(1, 1, "Project 1", "Description")

      // Create a project state with a project
      val state2 = new ProjectState().set(Some(project))

      // Verify that the state is not empty
      state2.isEmpty should be(false)
    }

  }
}
