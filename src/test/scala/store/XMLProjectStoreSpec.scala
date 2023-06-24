package store

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import java.io.File
import de.htwg.scm.model.Project
import de.htwg.scm.store.XMLProjectStore

class XMLProjectStoreSpec extends AnyFlatSpec with Matchers {

  val xmlFilePath = "store/projects.xml"
  var store: XMLProjectStore = _

  def cleanupTestData(): Unit = {
    val file = new File(xmlFilePath)
    if (file.exists()) {
      file.delete()
    }
  }

  "An XMLProjectStore" should "create a new project and return the generated ID" in {
    cleanupTestData()

    store = new XMLProjectStore()

    val project = Project(0, 1, "New Project", "This is a new project.")

    val createdId = store.create(project)

    createdId should be(1)

    val retrievedProject = store.read(createdId)
    retrievedProject should be(Some(project.copy(id = createdId)))

    cleanupTestData()
  }

  it should "read an existing project by ID" in {
    cleanupTestData()

    store = new XMLProjectStore()

    val project = Project(1, 2, "Existing Project", "This is an existing project.")

    store.create(project)

    val retrievedProject = store.read(1)

    retrievedProject should be(Some(project))

    cleanupTestData()
  }

  it should "update an existing project by ID" in {
    cleanupTestData()

    store = new XMLProjectStore()

    val project = Project(1, 2, "Old Project", "This is an old project.")

    store.create(project)

    val updatedProject = Project(1, 2, "Updated Project", "This is an updated project.")

    val result = store.update(1, updatedProject)

    result should be(1)

    val retrievedProject = store.read(1)
    retrievedProject should be(Some(updatedProject))

    cleanupTestData()
  }

  it should "delete a project by ID" in {
    cleanupTestData()

    store = new XMLProjectStore()

    val project = Project(1, 2, "Project to Delete", "This is a project to delete.")

    store.create(project)

    val result = store.delete(1)

    result should be(1)

    val retrievedProject = store.read(1)
    retrievedProject should be(None)

    cleanupTestData()
  }

  it should "retrieve all projects" in {
    cleanupTestData()

    store = new XMLProjectStore()

    val project1 = Project(1, 1, "Project 1", "This is project 1.")
    val project2 = Project(2, 2, "Project 2", "This is project 2.")

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

    store = new XMLProjectStore()

    val project1 = Project(1, 1, "Project 1", "This is project 1.")
    val project2 = Project(2, 2, "Project 2", "This is project 2.")

    store.create(project1)
    store.create(project2)

    val lastId = store.getLastId

    lastId should be(2)

    cleanupTestData()
  }
}
