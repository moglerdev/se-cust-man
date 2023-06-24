package controller

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import de.htwg.scm.controller.TaskController
import de.htwg.scm.model.{Project, Task}
import de.htwg.scm.store.ITaskStore

class TaskControllerSpec extends AnyFlatSpec with Matchers {

  class MockTaskStore extends ITaskStore {
    // Mock implementation of ITaskStore methods

    override def getAll: List[Task] = List(
      Task(1, "Task 1", "Description 1", 1),
      Task(2, "Task 2", "Description 2", 1),
      Task(3, "Task 3", "Description 3", 2),
      Task(4, "Task 4", "Description 4", 2)
    )
  }

  "A TaskController" should "retrieve all tasks associated with a project" in {
    val store = new MockTaskStore
    val controller = new TaskController(store)
    val project = Project(1, "Project 1", "Description 1", 1)

    val tasks = controller.getAllByProject(project)

    tasks should have length 2
    tasks.foreach(_.project_id should be(project.id))
  }

  it should "filter tasks based on title and description criteria" in {
    val store = new MockTaskStore
    val controller = new TaskController(store)

    val filteredTasks = controller.filter(Some("Task 2"), Some("Description"))

    filteredTasks should have length 1
    filteredTasks.head.title should be("Task 2")

    val emptyFilterTasks = controller.filter(None, None)

    emptyFilterTasks should have length 4
  }
}
