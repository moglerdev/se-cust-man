package de.htwg.scm
package store

import model.CustomerBuilder
import store.JSONCustomerStore
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.mockito.MockitoSugar.mock

import java.io.{File, FileWriter}
import scala.io.Source

class JSONCustomerStoreSpec extends AnyWordSpec with Matchers {
  "JSONCustomerStore" should {
    val jsonFilePath = "store/customers.json"
    val customer = CustomerBuilder().build()

    "create a new customer and return the assigned ID" in {
      val fileWriterMock = mock[FileWriter]
      val jsonFileMock = mock[File]

//      whenNew[FileWriter](jsonFilePath).thenReturn(fileWriterMock)
//      whenNew[File](jsonFilePath).thenReturn(jsonFileMock)

      val store = new JSONCustomerStore()

      val lastId = store.getLastId
      val newCustomerId = store.create(customer)

      newCustomerId shouldEqual lastId + 1
    }

    "read an existing customer by ID" in {
      val store = new JSONCustomerStore()

      val readCustomer = store.read(0)

      readCustomer shouldBe None
    }

    "return None when reading a non-existent customer by ID" in {
      val store = new JSONCustomerStore()

      val readCustomer = store.read(99)

      readCustomer shouldBe None
    }

    "update an existing customer by ID" in {
      val fileWriterMock = mock[FileWriter]
      val jsonFileMock = mock[File]

//      whenNew[FileWriter](jsonFilePath).thenReturn(fileWriterMock)
//      whenNew[File](jsonFilePath).thenReturn(jsonFileMock)

      val store = new JSONCustomerStore()

      val updatedCustomerId = store.update(0, customer)

      updatedCustomerId shouldEqual -1
    }

    "return -1 when updating a customer with an invalid ID" in {
      val store = new JSONCustomerStore()

      val updatedCustomerId = store.update(99, customer)

      updatedCustomerId shouldEqual -1
    }

    "delete an existing customer by ID" in {
      val fileWriterMock = mock[FileWriter]
      val jsonFileMock = mock[File]

//      whenNew[FileWriter](jsonFilePath).thenReturn(fileWriterMock)
//      whenNew[File](jsonFilePath).thenReturn(jsonFileMock)

      val store = new JSONCustomerStore()

      val deletedCustomerId = store.delete(0)

      deletedCustomerId shouldEqual -1
    }

    "return -1 when deleting a customer with an invalid ID" in {
      val store = new JSONCustomerStore()

      val deletedCustomerId = store.delete(99)

      deletedCustomerId shouldEqual -1
    }

    "delete an existing customer by model" in {
      val fileWriterMock = mock[FileWriter]
      val jsonFileMock = mock[File]

//      whenNew[FileWriter](jsonFilePath).thenReturn(fileWriterMock)
//      whenNew[File](jsonFilePath).thenReturn(jsonFileMock)

      val store = new JSONCustomerStore()

      val deletedCustomerId = store.delete(customer)

      deletedCustomerId shouldEqual -1
    }

    "return an empty list when there are no customers" in {
      val store = new JSONCustomerStore()

      val customers = store.getAll

      customers shouldBe empty
    }

    "return the last ID when there are existing customers" in {
      val store = new JSONCustomerStore()

      val lastId = store.getLastId

      lastId shouldEqual 0
    }
  }
}
