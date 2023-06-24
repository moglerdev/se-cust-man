package store

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import java.io.{File, PrintWriter}
import de.htwg.scm.model.{Customer, CustomerBuilder}
import de.htwg.scm.store.JSONCustomerStore

class JSONCustomerStoreSpec extends AnyFlatSpec with Matchers {

  val jsonFilePath = "store/customers.json"
  var store: JSONCustomerStore = _

  def prepareTestData(): Unit = {
    val customers = List(
      CustomerBuilder().setId(0).setName("John Doe").setEmail("john@example.com").setAddress("123 Main St").setPhone("555-1234").build(),
      CustomerBuilder().setId(1).setName("Jane Smith").setEmail("jane@example.com").setAddress("456 Elm St").setPhone("555-5678").build()
    )

    val jsonString = customers.map(_.asJson).mkString("\n")

    val writer = new PrintWriter(new File(jsonFilePath))
    writer.write(jsonString)
    writer.close()
  }

  def cleanupTestData(): Unit = {
    val file = new File(jsonFilePath)
    if (file.exists()) {
      file.delete()
    }
  }

  "A JSONCustomerStore" should "create a new customer and return the index" in {
    cleanupTestData()

    store = new JSONCustomerStore()

    val customer = CustomerBuilder().setName("John Doe").setEmail("john@example.com").setAddress("123 Main St").setPhone("555-1234").build()

    val createdIndex = store.create(customer)

    createdIndex should be(0)

    val retrievedCustomer = store.read(createdIndex)
    retrievedCustomer should be(Some(customer))

    cleanupTestData()
  }

  it should "read an existing customer by index" in {
    prepareTestData()

    store = new JSONCustomerStore()

    val retrievedCustomer = store.read(1)

    retrievedCustomer should be(Some(CustomerBuilder().setId(1).setName("Jane Smith").setEmail("jane@example.com").setAddress("456 Elm St").setPhone("555-5678").build()))

    cleanupTestData()
  }

  it should "update an existing customer by index" in {
    prepareTestData()

    store = new JSONCustomerStore()

    val updatedCustomer = CustomerBuilder().setId(0).setName("John Doe Updated").setEmail("john@example.com").setAddress("123 Main St").setPhone("555-1234").build()

    val updatedIndex = store.update(0, updatedCustomer)

    updatedIndex should be(0)

    val retrievedCustomer = store.read(0)
    retrievedCustomer should be(Some(updatedCustomer))

    cleanupTestData()
  }

  it should "delete a customer by index" in {
    prepareTestData()

    store = new JSONCustomerStore()

    val deletedIndex = store.delete(1)

    deletedIndex should be(1)

    val retrievedCustomer = store.read(1)
    retrievedCustomer should be(None)

    cleanupTestData()
  }

  it should "retrieve all customers" in {
    prepareTestData()

    store = new JSONCustomerStore()

    val customers = store.getAll

    customers should have size 2

    cleanupTestData()
  }

  it should "retrieve the last inserted customer's ID" in {
    prepareTestData()

    store = new JSONCustomerStore()

    val lastId = store.getLastId

    lastId should be(1)

    cleanupTestData()
  }
}
