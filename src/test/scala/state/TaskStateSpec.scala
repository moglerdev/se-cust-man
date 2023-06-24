package de.htwg.scm
package state

import model.Task
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.WordSpec

class TaskStateSpec extends WordSpec with Matchers {

  "TaskState" should {

    "set and retrieve the task" in {
      // Create a task
      val task = Task(1, 1, "Task 1", "Description")

      // Create a task state
      val state = new TaskState()

      // Set the task
      state.set(Some(task))

      // Retrieve the task
      val retrievedTask = state.get

      // Verify that the retrieved task matches the original task
      retrievedTask should be(task)
    }

    "return the task option" in {
      // Create a task
      val task = Task(1, 1, "Task 1", "Description")

      // Create a task state
      val state = new TaskState()

      // Set the task
      state.set(Some(task))

      // Retrieve the task option
      val taskOption = state.option

      // Verify that the task option is Some(task)
      taskOption should be(Some(task))
    }

    "return if the task state is defined" in {
      // Create a task state without a task
      val state1 = new TaskState()

      // Verify that the state is not defined
      state1.isDefined should be(false)

      // Create a task
      val task = Task(1, 1, "Task 1", "Description")

      // Create a task state with a task
      val state2 = new TaskState().set(Some(task))

      // Verify that the state is defined
      state2.isDefined should be(true)
    }

    "return if the task state is empty" in {
      // Create a task state without a task
      val state1 = new TaskState()

      // Verify that the state is empty
      state1.isEmpty should be(true)

      // Create a task
      val task = Task(1, 1, "Task 1", "Description")

      // Create a task state with a task
      val state2 = new TaskState().set(Some(task))

      // Verify that the state is not empty
      state2.isEmpty should be(false)
    }

  }
}
