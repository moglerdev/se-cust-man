package de.htwg.scm
package model

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.WordSpec

class CustomerBuilderSpec extends WordSpec with Matchers {

  "CustomerBuilder" should {

    "build a customer object" in {
      // Test data
      val id = 1
      val name = "John Doe"
      val email = "john.doe@example.com"
      val phone = "1234567890"
      val address = "123 Main St"

      // Create a CustomerBuilder instance
      val customerBuilder = new CustomerBuilder()
        .setId(id)
        .setName(name)
        .setEmail(email)
        .setPhoneNumber(phone)
        .setAddress(address)

      // Build the customer object
      val customer = customerBuilder.build()

      // Verify the customer object
      customer.id should be(id)
      customer.name should be(name)
      customer.email should be(email)
      customer.phone should be(phone)
      customer.address should be(address)
    }

    "build an empty customer object" in {
      // Create an empty CustomerBuilder instance
      val customerBuilder = new CustomerBuilder()

      // Build the empty customer object
      val emptyCustomer = customerBuilder.build()

      // Verify the empty customer object
      emptyCustomer.id should be(-1)
      emptyCustomer.name should be("")
      emptyCustomer.email should be("")
      emptyCustomer.phone should be("")
      emptyCustomer.address should be("")
    }

    "build a customer object from an existing customer" in {
      // Test data
      val existingCustomer = Customer(1, "John Doe", "john.doe@example.com", "1234567890", "123 Main St")

      // Create a CustomerBuilder instance from the existing customer
      val customerBuilder = new CustomerBuilder(existingCustomer)

      // Build the customer object
      val customer = customerBuilder.build()

      // Verify the customer object
      customer should be(existingCustomer)
    }
  }
}
