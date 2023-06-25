package de.htwg.scm
package controller

import org.scalatestplus.mockito.MockitoSugar
import store.ITaskStore
import model.{Project, Task}

import org.mockito.Mockito.{times, verify, when}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class TaskControllerSpec extends AnyWordSpec with Matchers with MockitoSugar {

  // Create a mock store
  val mockStore: ITaskStore = mock[ITaskStore]

  // Create an instance of the TaskController with the mock store
  val taskController = new TaskController(mockStore)

  "TaskController" should {

    "get all tasks by project" in {
      // Test data
      val project = Project(1, 1, "Test Project", "Description")
      val tasks = List(
        Task(1, project.id, "Task 1", "Description 1"),
        Task(2, project.id, "Task 2", "Description 2")
      )

      // Set up the mock store behavior
      when(mockStore.getAll).thenReturn(tasks)

      // Call the getAllByProject method
      val result = taskController.getAllByProject(project)

      // Verify the mock store interactions
      verify(mockStore, times(1)).getAll

      // Verify the result
      result should be(tasks)
    }

    "filter tasks by title and description" in {
      // Test data
      val titleFilter = Some("Task 1")
      val descriptionFilter = Some("Description")

      // Set up the mock store behavior
      val tasks = List(
        Task(1, 1, "Task 1", "Description 1"),
        Task(2, 2, "Task 2", "Description 2"),
        Task(3, 3, "Task 3", "Other Description")
      )
      when(mockStore.getAll).thenReturn(tasks)

      // Call the filter method
      val result = taskController.filter(titleFilter, descriptionFilter)

      // Verify the mock store interactions
      verify(mockStore, times(2)).getAll

      // Verify the result
      result should be(List(tasks.head))
    }
  }
}
