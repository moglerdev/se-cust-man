package store

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import java.sql.{Connection, DriverManager, Statement}
import de.htwg.scm.model.{Project, Task}
import de.htwg.scm.store.DBTaskStore

class DBTaskStoreSpec extends AnyFlatSpec with Matchers {

  val url = "jdbc:mysql://localhost:3306/mydatabase"
  val username = "username"
  val password = "password"

  var connection: Connection = _
  var store: DBTaskStore = _

  def initializeDatabase(): Unit = {
    connection = DriverManager.getConnection(url, username, password)
    val statement: Statement = connection.createStatement()

    // Create the task table
    statement.execute("CREATE TABLE task (id INT AUTO_INCREMENT PRIMARY KEY, project_id INT, title VARCHAR(100), description VARCHAR(200))")

    // Insert test data
    statement.execute("INSERT INTO task (project_id, title, description) VALUES (1, 'Task 1', 'Description 1')")
    statement.execute("INSERT INTO task (project_id, title, description) VALUES (1, 'Task 2', 'Description 2')")
  }

  def cleanupDatabase(): Unit = {
    val statement: Statement = connection.createStatement()

    // Delete the task table
    statement.execute("DROP TABLE IF EXISTS task")

    // Close the connection
    if (connection != null) {
      connection.close()
    }
  }

  "A DBTaskStore" should "create a new task in the database" in {
    initializeDatabase()

    store = new DBTaskStore()

    val task = Task.empty(Project(1, 1, "", ""))
    task.title = "New Task"
    task.description = "New Task Description"

    val createdId = store.create(task)

    createdId should be > 0

    val retrievedTask = store.read(createdId)
    retrievedTask should be(Some(task))

    cleanupDatabase()
  }

  it should "update an existing task in the database" in {
    initializeDatabase()

    store = new DBTaskStore()

    val task = Task(1, 1, "Task 1", "Description 1")

    val updatedId = store.update(task.id, task)

    updatedId should be > 0

    val retrievedTask = store.read(task.id)
    retrievedTask should be(Some(task))

    cleanupDatabase()
  }

  it should "delete a task from the database" in {
    initializeDatabase()

    store = new DBTaskStore()

    val task = Task(2, 1, "Task 2", "Description 2")

    val deletedCount = store.delete(task)

    deletedCount should be > 0

    val retrievedTask = store.read(task.id)
    retrievedTask should be(None)

    cleanupDatabase()
  }

  it should "retrieve all tasks from the database" in {
    initializeDatabase()

    store = new DBTaskStore()

    val tasks = store.getAll

    tasks should have size 2

    cleanupDatabase()
  }

  it should "retrieve the last inserted task's ID" in {
    initializeDatabase()

    store = new DBTaskStore()

    val lastId = store.getLastId

    lastId should be > 0

    cleanupDatabase()
  }
}
