package de.htwg.scm
package controller

import org.scalatestplus.mockito.MockitoSugar
import store.IProjectStore

import model.{Customer, Project, Task}
import org.mockito.Mockito.{times, verify, when}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.WordSpec

class ProjectControllerSpec extends WordSpec with Matchers with MockitoSugar {
  "ProjectController" should {

    "get all projects by customer" in {

      // Create a mock store
      val mockStore: IProjectStore = mock[IProjectStore]

      // Create an instance of the ProjectController with the mock store
      val projectController = new ProjectController(mockStore)


      // Test data
      val customer = Customer.empty.copy(id = 1, name = "Test Customer")
      val projects = List(
        Project(1, customer.id, "Project 1", "Description 1"),
        Project(2, customer.id, "Project 2", "Description 2")
      )

      // Set up the mock store behavior
      when(mockStore.getAll).thenReturn(projects)

      // Call the getAllByCustomer method
      val result = projectController.getAllByCustomer(customer)

      // Verify the mock store interactions
      verify(mockStore, times(1)).getAll

      // Verify the result
      result should be(projects)
    }

    "get project by task" in {

      // Create a mock store
      val mockStore: IProjectStore = mock[IProjectStore]

      // Create an instance of the ProjectController with the mock store
      val projectController = new ProjectController(mockStore)

      // Test data
      val task = Task(1, 1, "Task 1", "Description 1")

      // Set up the mock store behavior
      val projects = List(
        Project(1, 1, "Project 1", "Description 1"),
        Project(2, 2, "Project 2", "Description 2")
      )
      when(mockStore.getAll).thenReturn(projects)

      // Call the getByTask method
      val result = projectController.getByTask(task)

      // Verify the mock store interactions
      verify(mockStore, times(1)).getAll

      // Verify the result
      result should be(Some(projects.head))
    }

    "filter projects by title and description" in {

      // Create a mock store
      val mockStore: IProjectStore = mock[IProjectStore]

      // Create an instance of the ProjectController with the mock store
      val projectController = new ProjectController(mockStore)

      // Test data
      val titleFilter = Some("Project 1")
      val descriptionFilter = Some("Description")

      // Set up the mock store behavior
      val projects = List(
        Project(1, 1, "Project 1", "Description 1"),
        Project(2, 2, "Project 2", "Description 2"),
        Project(3, 3, "Project 3", "Other Description")
      )
      when(mockStore.getAll).thenReturn(projects)

      // Call the filter method
      val result = projectController.filter(titleFilter, descriptionFilter)

      // Verify the mock store interactions
      verify(mockStore, times(1)).getAll

      // Verify the result
      result should be(List(projects.head))
    }
  }
}
