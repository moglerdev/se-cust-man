package model

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import de.htwg.scm.model.Customer

class CustomerBuilderSpec extends AnyFlatSpec with Matchers {

  "A CustomerBuilder" should "build a Customer object with the specified values" in {
    val builder = new CustomerBuilder()
      .setId(1)
      .setName("John Doe")
      .setEmail("john.doe@example.com")
      .setPhoneNumber("1234567890")
      .setAddress("123 Main St")

    val customer = builder.build()

    customer.id should be(1)
    customer.name should be("John Doe")
    customer.email should be("john.doe@example.com")
    customer.phone should be("1234567890")
    customer.address should be("123 Main St")
  }

  it should "build a Customer object with default values if not specified" in {
    val builder = new CustomerBuilder()

    val customer = builder.build()

    customer.id should be(-1)
    customer.name should be("")
    customer.email should be("")
    customer.phone should be("")
    customer.address should be("")
  }

  it should "build a Customer object based on an existing Customer" in {
    val existingCustomer = Customer(1, "Jane Smith", "jane.smith@example.com", "9876543210", "456 Oak St")
    val builder = new CustomerBuilder(existingCustomer)
      .setId(2)
      .setEmail("jane.smith.new@example.com")

    val customer = builder.build()

    customer.id should be(2)
    customer.name should be("Jane Smith")
    customer.email should be("jane.smith.new@example.com")
    customer.phone should be("9876543210")
    customer.address should be("456 Oak St")
  }
}
