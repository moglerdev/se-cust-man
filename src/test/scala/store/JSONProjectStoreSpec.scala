package de.htwg.scm
package store

import de.htwg.scm.model.Project
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.BeforeAndAfter
import org.scalatest.matchers.should.Matchers

import java.nio.file.{Files, Paths}

class JSONProjectStoreSpec extends AnyFlatSpec with Matchers with BeforeAndAfter {
  val testFilePath: String = "test_projects.json"
  var projectStore: JSONProjectStore = _

  before {
    // Create a new JSONProjectStore instance for each test case
    projectStore = new JSONProjectStore()
  }

  after {
    // Delete the test file after each test case
    Files.deleteIfExists(Paths.get(testFilePath))
  }

  "JSONProjectStore" should "create a new project" in {
    val project = Project(1, 1, "Test Project", "Description")
    val projectId = projectStore.create(project)

    projectId should be > 0
    val retrievedProject = projectStore.read(projectId)
    retrievedProject shouldBe Some(project.copy(id = projectId))
  }

  it should "read an existing project by ID" in {
    val project = Project(1, 1, "Test Project", "Description")
    val projectId = projectStore.create(project)

    val retrievedProject = projectStore.read(projectId)
    retrievedProject shouldBe Some(project.copy(id = projectId))
  }

  it should "return None when reading a non-existing project" in {
    val retrievedProject = projectStore.read(999)
    retrievedProject shouldBe None
  }

  it should "update an existing project" in {
    val project = Project(1, 1, "Test Project", "Description")
    val projectId = projectStore.create(project)

    val updatedProject = Project(projectId, 1,"Updated Project", "Updated Description")
    val result = projectStore.update(projectId, updatedProject)

    result shouldBe 1
    val retrievedProject = projectStore.read(projectId)
    retrievedProject shouldBe Some(updatedProject)
  }

  it should "return 0 when updating a non-existing project" in {
    val result = projectStore.update(999, Project(999, 1, "Updated Project", "Updated Description"))
    result shouldBe 0
  }

  it should "delete an existing project by ID" in {
    val project = Project(1, 1, "Test Project", "Description")
    val projectId = projectStore.create(project)

    val result = projectStore.delete(projectId)
    result shouldBe 1
    val retrievedProject = projectStore.read(projectId)
    retrievedProject shouldBe None
  }

  it should "return 0 when deleting a non-existing project" in {
    val result = projectStore.delete(999)
    result shouldBe 0
  }

  it should "delete an existing project by model" in {
    val project = Project(1, 1, "Test Project", "Description")
    val projectId = projectStore.create(project)

    val result = projectStore.delete(project)
    result shouldBe 1
    val retrievedProject = projectStore.read(projectId)
    retrievedProject shouldBe None
  }

  it should "return all projects" in {
    val project1 = Project(1, 1, "Project 1", "Description 1")
    val project2 = Project(2, 1, "Project 2", "Description 2")
    val project3 = Project(3, 1, "Project 3", "Description 3")
    projectStore.create(project1)
    projectStore.create(project2)
    projectStore.create(project3)

    val allProjects = projectStore.getAll
    allProjects should contain theSameElementsAs List(project1, project2, project3)
  }

  it should "return the last project ID" in {
    val project1 = Project(1, 1, "Project 1", "Description 1")
    val project2 = Project(2, 1, "Project 2", "Description 2")
    projectStore.create(project1)
    projectStore.create(project2)

    val lastId = projectStore.getLastId
    lastId shouldBe 2
  }
}
