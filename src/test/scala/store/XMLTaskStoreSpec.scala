package store

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import java.io.File
import de.htwg.scm.model.Task
import de.htwg.scm.store.XMLTaskStore

class XMLTaskStoreSpec extends AnyFlatSpec with Matchers {

  val xmlFilePath = "store/tasks.xml"
  var store: XMLTaskStore = _

  def cleanupTestData(): Unit = {
    val file = new File(xmlFilePath)
    if (file.exists()) {
      file.delete()
    }
  }

  "An XMLTaskStore" should "create a new task and return the generated ID" in {
    cleanupTestData()

    store = new XMLTaskStore()

    val task = Task(0, 1, "New Task", "This is a new task.")

    val createdId = store.create(task)

    createdId should be(1)

    val retrievedTask = store.read(createdId)
    retrievedTask should be(Some(task.copy(id = createdId)))

    cleanupTestData()
  }

  it should "read an existing task by ID" in {
    cleanupTestData()

    store = new XMLTaskStore()

    val task = Task(1, 1, "Existing Task", "This is an existing task.")

    store.create(task)

    val retrievedTask = store.read(1)

    retrievedTask should be(Some(task))

    cleanupTestData()
  }

  it should "update an existing task by ID" in {
    cleanupTestData()

    store = new XMLTaskStore()

    val task = Task(1, 1, "Old Task", "This is an old task.")

    store.create(task)

    val updatedTask = Task(1, 1, "Updated Task", "This is an updated task.")

    val result = store.update(1, updatedTask)

    result should be(1)

    val retrievedTask = store.read(1)
    retrievedTask should be(Some(updatedTask))

    cleanupTestData()
  }

  it should "delete a task by ID" in {
    cleanupTestData()

    store = new XMLTaskStore()

    val task = Task(1, 1, "Task to Delete", "This is a task to delete.")

    store.create(task)

    val result = store.delete(1)

    result should be(1)

    val retrievedTask = store.read(1)
    retrievedTask should be(None)

    cleanupTestData()
  }

  it should "retrieve all tasks" in {
    cleanupTestData()

    store = new XMLTaskStore()

    val task1 = Task(1, 1, "Task 1", "This is task 1.")
    val task2 = Task(2, 1, "Task 2", "This is task 2.")

    store.create(task1)
    store.create(task2)

    val tasks = store.getAll

    tasks should have size 2

    tasks should contain(task1)
    tasks should contain(task2)

    cleanupTestData()
  }

  it should "retrieve the last inserted task's ID" in {
    cleanupTestData()

    store = new XMLTaskStore()

    val task1 = Task(1, 1, "Task 1", "This is task 1.")
    val task2 = Task(2, 1, "Task 2", "This is task 2.")

    store.create(task1)
    store.create(task2)

    val lastId = store.getLastId

    lastId should be(2)

    cleanupTestData()
  }
}
