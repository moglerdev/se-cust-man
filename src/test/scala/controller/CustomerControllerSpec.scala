package controller

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

package de.htwg.scm.controller

import model.{Customer, Project}
import store.ICustomerStore

class CustomerControllerSpec extends AnyFlatSpec with Matchers {

  // Mock customer store implementation for testing
  class MockCustomerStore extends ICustomerStore {
    private var customers: List[Customer] = List.empty

    def getAll: List[Customer] = customers
    def create(customer: Customer): Int = {
      customers = customers :+ customer
      customer.id // Simulate assigning an ID to the customer
    }
    def delete(customer: Customer): Int = {
      customers = customers.filterNot(_.id == customer.id)
      1 // Simulate successful deletion
    }
    def update(id: Int, customer: Customer): Int = {
      customers.find(_.id == id) match {
        case Some(existingCustomer) =>
          customers = customers.filterNot(_.id == id) :+ customer
          1 // Simulate successful update
        case None => 0 // Simulate failure to find the customer
      }
    }
    def read(id: Int): Option[Customer] = customers.find(_.id == id)
  }

  "A CustomerController" should "retrieve the customer associated with the specified project" in {
    // Create a test customer and project
    val customer = Customer(1, "John Doe", "john@example.com", "123456789")
    val project = Project(1, "Project 1", "Project description", customer.id)

    // Create a mock customer store
    val store = new MockCustomerStore
    store.create(customer)

    // Create a CustomerController with the mock store
    val controller = new CustomerController(store)

    // Retrieve the customer associated with the project
    val result = controller.getByProject(project)

    // Verify that the correct customer was retrieved
    result should be(Some(customer))
  }

  it should "filter the customers based on the specified criteria" in {
    // Create test customers
    val customer1 = Customer(1, "John Doe", "john@example.com", "123456789")
    val customer2 = Customer(2, "Alice Smith", "alice@example.com", "987654321")
    val customer3 = Customer(3, "Bob Johnson", "bob@example.com", "456123789")

    // Create a mock customer store
    val store = new MockCustomerStore
    store.create(customer1)
    store.create(customer2)
    store.create(customer3)

    // Create a CustomerController with the mock store
    val controller = new CustomerController(store)

    // Filter the customers by name and email
    val filteredCustomers = controller.filter(Some("John"), Some("example.com"), None)

    // Verify that the correct customers were filtered
    filteredCustomers should contain theSameElementsAs List(customer1)
  }
}
