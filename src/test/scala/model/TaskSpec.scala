package de.htwg.scm
package model

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import model.{Project, Task}

class TaskSpec extends AnyFlatSpec with Matchers {

  "An empty Task" should "have default values" in {
    val project = Project(1, 1, "Project X", "Description of Project X")
    val emptyTask = Task.empty(project)

    emptyTask.id should be(-1)
    emptyTask.project_id should be(project.id)
    emptyTask.title should be("")
    emptyTask.description should be("")
  }

  it should "retain the project ID when created with empty title and description" in {
    val project = Project(1, 1, "Project X", "Description of Project X")
    val emptyTask = Task.empty(project)

    emptyTask.project_id should be(project.id)
  }

  it should "allow setting values for ID, title, and description" in {
    val project = Project(1, 1, "Project X", "Description of Project X")
    val task = Task.empty(project).copy(id = 2, title = "Task A", description = "Description of Task A")

    task.id should be(2)
    task.title should be("Task A")
    task.description should be("Description of Task A")
  }
}
