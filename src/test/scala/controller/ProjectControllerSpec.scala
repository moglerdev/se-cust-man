package controller

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import de.htwg.scm.controller.ProjectController
import de.htwg.scm.model.{Customer, Project, Task}
import de.htwg.scm.store.IProjectStore

class ProjectControllerSpec extends AnyFlatSpec with Matchers {

  // Mock store implementation for testing
  class MockProjectStore extends IProjectStore {
    private var projects: List[Project] = List.empty

    def create(project: Project): Int = {
      val id = projects.length
      projects = projects :+ project.copy(id = id)
      id // Return the assigned ID
    }

    def update(id: Int, project: Project): Int = {
      projects = projects.map {
        case p if p.id == id => project.copy(id = id)
        case p => p
      }
      1 // Simulate successful update
    }

    def delete(project: Project): Int = {
      projects = projects.filterNot(_ == project)
      1 // Simulate successful deletion
    }

    def read(id: Int): Option[Project] = projects.find(_.id == id)

    def getAll: List[Project] = projects
  }

  "A ProjectController" should "retrieve all projects by customer" in {
    // Create test projects
    val customer = Customer(1, "John Doe", "john.doe@example.com", "1234567890", "Some Address")
    val project1 = Project(1, "Project 1", "Description 1", customer.id)
    val project2 = Project(2, "Project 2", "Description 2", customer.id)

    // Create a mock project store
    val store = new MockProjectStore
    store.create(project1)
    store.create(project2)

    // Create a ProjectController with the mock store
    val controller = new ProjectController(store) {}

    // Retrieve all projects by customer
    val allProjects = controller.getAllByCustomer(customer)

    // Verify that all projects associated with the customer are retrieved
    allProjects should contain theSameElementsAs List(project1, project2)
  }

  it should "retrieve a project by task" in {
    // Create test projects
    val project1 = Project(1, "Project 1", "Description 1", 1)
    val project2 = Project(2, "Project 2", "Description 2", 2)
    val task = Task(1, "Task 1", "Task Description", project2.id)

    // Create a mock project store
    val store = new MockProjectStore
    store.create(project1)
    store.create(project2)

    // Create a ProjectController with the mock store
    val controller = new ProjectController(store) {}

    // Retrieve the project associated with the task
    val retrievedProject = controller.getByTask(task)

    // Verify that the correct project is retrieved
    retrievedProject should be(Some(project2))
  }

  it should "filter projects based on title and description" in {
    // Create test projects
    val project1 = Project(1, "Project 1", "Description 1", 1)
    val project2 = Project(2, "Project 2", "Description 2", 2)
    val project3 = Project(3, "Another Project", "Some Description", 1)

    // Create a mock project store
    val store = new MockProjectStore
    store.create(project1)
    store.create(project2)
    store.create(project3)

    // Create a ProjectController with the mock store
    val controller = new ProjectController(store) {}

    // Filter projects by title and description
    val filteredProjects = controller.filter(Some("Project"), Some("Description"))

    // Verify that the correct projects are filtered
    filteredProjects should contain theSameElementsAs List(project1, project2)
  }
}
