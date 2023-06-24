package store

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import java.sql.{Connection, DriverManager, Statement}
import de.htwg.scm.model.{Customer, CustomerBuilder}
import de.htwg.scm.store.DBCustomerStore

class DBCustomerStoreSpec extends AnyFlatSpec with Matchers {

  val url = "jdbc:mysql://localhost:3306/mydatabase"
  val username = "username"
  val password = "password"

  var connection: Connection = _
  var store: DBCustomerStore = _

  def initializeDatabase(): Unit = {
    connection = DriverManager.getConnection(url, username, password)
    val statement: Statement = connection.createStatement()

    // Create the customer table
    statement.execute("CREATE TABLE customer (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(100), email VARCHAR(100), address VARCHAR(100), phone VARCHAR(20))")

    // Insert test data
    statement.execute("INSERT INTO customer (name, email, address, phone) VALUES ('John Doe', 'john@example.com', '123 Main St', '123456789')")
    statement.execute("INSERT INTO customer (name, email, address, phone) VALUES ('Jane Smith', 'jane@example.com', '456 Elm St', '987654321')")
  }

  def cleanupDatabase(): Unit = {
    val statement: Statement = connection.createStatement()

    // Delete the customer table
    statement.execute("DROP TABLE IF EXISTS customer")

    // Close the connection
    if (connection != null) {
      connection.close()
    }
  }

  "A DBCustomerStore" should "create a new customer in the database" in {
    initializeDatabase()

    store = new DBCustomerStore()

    val customer = Customer.empty
    customer.name = "Alice Smith"
    customer.email = "alice@example.com"
    customer.address = "789 Oak St"
    customer.phone = "456789123"

    val createdId = store.create(customer)

    createdId should be > 0

    val retrievedCustomer = store.read(createdId)
    retrievedCustomer should be(Some(customer))

    cleanupDatabase()
  }

  it should "update an existing customer in the database" in {
    initializeDatabase()

    store = new DBCustomerStore()

    val customer = Customer(1, "John Doe", "john@example.com", "123 Main St", "123456789")

    val updatedId = store.update(customer.id, customer)

    updatedId should be > 0

    val retrievedCustomer = store.read(customer.id)
    retrievedCustomer should be(Some(customer))

    cleanupDatabase()
  }

  it should "delete a customer from the database" in {
    initializeDatabase()

    store = new DBCustomerStore()

    val customer = Customer(2, "Jane Smith", "jane@example.com", "456 Elm St", "987654321")

    val deletedCount = store.delete(customer)

    deletedCount should be > 0

    val retrievedCustomer = store.read(customer.id)
    retrievedCustomer should be(None)

    cleanupDatabase()
  }

  it should "retrieve all customers from the database" in {
    initializeDatabase()

    store = new DBCustomerStore()

    val customers = store.getAll

    customers should have size 2

    cleanupDatabase()
  }

  it should "retrieve the last inserted customer's ID" in {
    initializeDatabase()

    store = new DBCustomerStore()

    val lastId = store.getLastId

    lastId should be > 0

    cleanupDatabase()
  }
}
