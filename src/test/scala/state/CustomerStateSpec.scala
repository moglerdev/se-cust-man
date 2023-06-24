package de.htwg.scm
package state

import model.Customer
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class CustomerStateSpec extends AnyWordSpec with Matchers {

  "CustomerState" should {

    "set and retrieve the customer" in {
      // Create a customer
      val customer = Customer(1, "John Doe", "john@example.com", "1234567890", "123 Main St")

      // Create a customer state
      val state = new CustomerState()

      // Set the customer
      state.set(Some(customer))

      // Retrieve the customer
      val retrievedCustomer = state.get

      // Verify that the retrieved customer matches the original customer
      retrievedCustomer should be(customer)
    }

    "return the customer option" in {
      // Create a customer
      val customer = Customer(1, "John Doe", "john@example.com", "1234567890", "123 Main St")

      // Create a customer state
      val state = new CustomerState()

      // Set the customer
      state.set(Some(customer))

      // Retrieve the customer option
      val customerOption = state.option

      // Verify that the customer option is Some(customer)
      customerOption should be(Some(customer))
    }

    "return if the customer state is defined" in {
      // Create a customer state without a customer
      val state1 = new CustomerState()

      // Verify that the state is not defined
      state1.isDefined should be(false)

      // Create a customer
      val customer = Customer(1, "John Doe", "john@example.com", "1234567890", "123 Main St")

      // Create a customer state with a customer
      val state2 = new CustomerState().set(Some(customer))

      // Verify that the state is defined
      state2.isDefined should be(true)
    }

    "return if the customer state is empty" in {
      // Create a customer state without a customer
      val state1 = new CustomerState()

      // Verify that the state is empty
      state1.isEmpty should be(true)

      // Create a customer
      val customer = Customer(1, "John Doe", "john@example.com", "1234567890", "123 Main St")

      // Create a customer state with a customer
      val state2 = new CustomerState().set(Some(customer))

      // Verify that the state is not empty
      state2.isEmpty should be(false)
    }

  }
}
