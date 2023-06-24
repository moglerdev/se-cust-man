package store

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import java.sql.{Connection, DriverManager, Statement}
import de.htwg.scm.model.{Customer, Project}
import de.htwg.scm.store.DBProjectStore

class DBProjectStoreSpec extends AnyFlatSpec with Matchers {

  val url = "jdbc:mysql://localhost:3306/mydatabase"
  val username = "username"
  val password = "password"

  var connection: Connection = _
  var store: DBProjectStore = _

  def initializeDatabase(): Unit = {
    connection = DriverManager.getConnection(url, username, password)
    val statement: Statement = connection.createStatement()

    // Create the project table
    statement.execute("CREATE TABLE project (id INT AUTO_INCREMENT PRIMARY KEY, customer_id INT, title VARCHAR(100), description VARCHAR(200))")

    // Insert test data
    statement.execute("INSERT INTO project (customer_id, title, description) VALUES (1, 'Project 1', 'Description 1')")
    statement.execute("INSERT INTO project (customer_id, title, description) VALUES (2, 'Project 2', 'Description 2')")
  }

  def cleanupDatabase(): Unit = {
    val statement: Statement = connection.createStatement()

    // Delete the project table
    statement.execute("DROP TABLE IF EXISTS project")

    // Close the connection
    if (connection != null) {
      connection.close()
    }
  }

  "A DBProjectStore" should "create a new project in the database" in {
    initializeDatabase()

    store = new DBProjectStore()

    val project = Project.empty(Customer(1, "", "", "", ""))
    project.title = "New Project"
    project.description = "New Project Description"

    val createdId = store.create(project)

    createdId should be > 0

    val retrievedProject = store.read(createdId)
    retrievedProject should be(Some(project))

    cleanupDatabase()
  }

  it should "update an existing project in the database" in {
    initializeDatabase()

    store = new DBProjectStore()

    val project = Project(1, 1, "Project 1", "Description 1")

    val updatedId = store.update(project.id, project)

    updatedId should be > 0

    val retrievedProject = store.read(project.id)
    retrievedProject should be(Some(project))

    cleanupDatabase()
  }

  it should "delete a project from the database" in {
    initializeDatabase()

    store = new DBProjectStore()

    val project = Project(2, 2, "Project 2", "Description 2")

    val deletedCount = store.delete(project)

    deletedCount should be > 0

    val retrievedProject = store.read(project.id)
    retrievedProject should be(None)

    cleanupDatabase()
  }

  it should "retrieve all projects from the database" in {
    initializeDatabase()

    store = new DBProjectStore()

    val projects = store.getAll

    projects should have size 2

    cleanupDatabase()
  }

  it should "retrieve the last inserted project's ID" in {
    initializeDatabase()

    store = new DBProjectStore()

    val lastId = store.getLastId

    lastId should be > 0

    cleanupDatabase()
  }
}
