package de.htwg.scm
package controller

import org.scalatestplus.mockito.MockitoSugar
import org.mockito.Mockito.*
import store.ICustomerStore
import model.{Customer, Project}
import controller.CustomerController

import org.scalatest.funspec.AnyFunSpec

class CustomerControllerSpec extends AnyFunSpec with MockitoSugar {

  describe("CustomerController") {
    val mockCustomerStore: ICustomerStore = mock[ICustomerStore]
    val customerController: ICustomerController = new CustomerController(mockCustomerStore)

    val customer1 = Customer(1, "John Doe", "john@example.com", "1234567890", "123 Main St")
    val customer2 = Customer(2, "Jane Smith", "jane@example.com", "9876543210", "456 Elm St")
    val project = Project(1, 1, "Project 1", "Description")

    it("should return the customer associated with a project") {
      when(mockCustomerStore.getAll).thenReturn(List(customer1, customer2))
      val result = customerController.getByProject(project)
      assert(result.contains(customer1))
    }

    it("should filter customers based on name, email, and phone") {
      when(mockCustomerStore.getAll).thenReturn(List(customer1, customer2))
      val result = customerController.filter(Some("John"), None, Some("1234567890"))
      assert(result.size == 1)
      assert(result.head == customer1)
    }
  }
}
