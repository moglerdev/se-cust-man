package store

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import java.io.File
import de.htwg.scm.model.Customer
import de.htwg.scm.store.XMLCustomerStore

class XMLCustomerStoreSpec extends AnyFlatSpec with Matchers {

  val xmlFilePath = "store/customers.xml"
  var store: XMLCustomerStore = _

  def cleanupTestData(): Unit = {
    val file = new File(xmlFilePath)
    if (file.exists()) {
      file.delete()
    }
  }

  "An XMLCustomerStore" should "create a new customer and return the generated ID" in {
    cleanupTestData()

    store = new XMLCustomerStore()

    val customer = Customer(0, "John Doe", "john@example.com", "123 Main St", "555-1234")

    val createdId = store.create(customer)

    createdId should be(1)

    val retrievedCustomer = store.read(createdId)
    retrievedCustomer should be(Some(customer.copy(id = createdId)))

    cleanupTestData()
  }

  it should "read an existing customer by ID" in {
    cleanupTestData()

    store = new XMLCustomerStore()

    val customer = Customer(1, "Jane Smith", "jane@example.com", "456 Elm St", "555-5678")

    store.create(customer)

    val retrievedCustomer = store.read(1)

    retrievedCustomer should be(Some(customer))

    cleanupTestData()
  }

  it should "update an existing customer by ID" in {
    cleanupTestData()

    store = new XMLCustomerStore()

    val customer = Customer(1, "Old Customer", "old@example.com", "789 Oak St", "555-9012")

    store.create(customer)

    val updatedCustomer = Customer(1, "Updated Customer", "updated@example.com", "789 Oak St", "555-9012")

    val result = store.update(1, updatedCustomer)

    result should be(1)

    val retrievedCustomer = store.read(1)
    retrievedCustomer should be(Some(updatedCustomer))

    cleanupTestData()
  }

  it should "delete a customer by ID" in {
    cleanupTestData()

    store = new XMLCustomerStore()

    val customer = Customer(1, "Customer to Delete", "delete@example.com", "123 Elm St", "555-5555")

    store.create(customer)

    val result = store.delete(1)

    result should be(1)

    val retrievedCustomer = store.read(1)
    retrievedCustomer should be(None)

    cleanupTestData()
  }

  it should "retrieve all customers" in {
    cleanupTestData()

    store = new XMLCustomerStore()

    val customer1 = Customer(1, "Customer 1", "customer1@example.com", "123 Main St", "555-1111")
    val customer2 = Customer(2, "Customer 2", "customer2@example.com", "456 Elm St", "555-2222")

    store.create(customer1)
    store.create(customer2)

    val customers = store.getAll

    customers should have size 2

    customers should contain(customer1)
    customers should contain(customer2)

    cleanupTestData()
  }

  it should "retrieve the last inserted customer's ID" in {
    cleanupTestData()

    store = new XMLCustomerStore()

    val customer1 = Customer(1, "Customer 1", "customer1@example.com", "123 Main St", "555-1111")
    val customer2 = Customer(2, "Customer 2", "customer2@example.com", "456 Elm St", "555-2222")

    store.create(customer1)
    store.create(customer2)

    val lastId = store.getLastId

    lastId should be(2)

    cleanupTestData()
  }
}
