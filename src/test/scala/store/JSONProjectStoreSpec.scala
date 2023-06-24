package store

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import java.nio.file.{Files, Paths}
import de.htwg.scm.model.Project
import de.htwg.scm.store.JSONProjectStore

class JSONProjectStoreSpec extends AnyFlatSpec with Matchers {

  val filePath = "projects.json"
  var store: JSONProjectStore = _

  def cleanupTestData(): Unit = {
    val path = Paths.get(filePath)
    if (Files.exists(path)) {
      Files.delete(path)
    }
  }

  "A JSONProjectStore" should "create a new project and return the generated ID" in {
    cleanupTestData()

    store = new JSONProjectStore()

    val project = Project(0, "New Project", "This is a new project")

    val createdId = store.create(project)

    createdId should be(1)

    val retrievedProject = store.read(createdId)
    retrievedProject should be(Some(project.copy(id = createdId)))

    cleanupTestData()
  }

  it should "read an existing project by ID" in {
    cleanupTestData()

    store = new JSONProjectStore()

    val project = Project(1, "Test Project", "This is a test project")

    store.create(project)

    val retrievedProject = store.read(1)

    retrievedProject should be(Some(project))

    cleanupTestData()
  }

  it should "update an existing project by ID" in {
    cleanupTestData()

    store = new JSONProjectStore()

    val project = Project(1, "Old Project", "This is an old project")

    store.create(project)

    val updatedProject = Project(1, "Updated Project", "This is an updated project")

    val result = store.update(1, updatedProject)

    result should be(1)

    val retrievedProject = store.read(1)
    retrievedProject should be(Some(updatedProject))

    cleanupTestData()
  }

  it should "delete a project by ID" in {
    cleanupTestData()

    store = new JSONProjectStore()

    val project = Project(1, "Project to Delete", "This is a project to delete")

    store.create(project)

    val result = store.delete(1)

    result should be(1)

    val retrievedProject = store.read(1)
    retrievedProject should be(None)

    cleanupTestData()
  }

  it should "retrieve all projects" in {
    cleanupTestData()

    store = new JSONProjectStore()

    val project1 = Project(1, "Project 1", "This is project 1")
    val project2 = Project(2, "Project 2", "This is project 2")

    store.create(project1)
    store.create(project2)

    val projects = store.getAll

    projects should have size 2

    projects should contain(project1)
    projects should contain(project2)

    cleanupTestData()
  }

  it should "retrieve the last inserted project's ID" in {
    cleanupTestData()

    store = new JSONProjectStore()

    val project1 = Project(1, "Project 1", "This is project 1")
    val project2 = Project(2, "Project 2", "This is project 2")

    store.create(project1)
    store.create(project2)

    val lastId = store.getLastId

    lastId should be(2)

    cleanupTestData()
  }
}
