package de.htwg.se_cust_man.service

import org.scalatest.*
import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec
import de.htwg.se_cust_man.builder.{CustomerBuilder, ProjectBuilder, TaskBuilder}
import java.util.Date
import de.htwg.se_cust_man.service.CustomerService
import de.htwg.se_cust_man.Customer

class CustomerServiceSpec extends AnyWordSpec with Matchers {
  "Customer Service Test" should {
    val test = CustomerService.getInstance("test");
    "create a Customer" in {
      val customer = test.insertCustomer(Customer(1, "okay", 2, "okay", "okay"))
      customer.addressId must be(2)
      customer.email must be("okay")
      customer.name must be("okay")
      customer.phone must be("okay")
    }
    "get a Customer by id" in {
      val customer = test.getCustomerById(1)
      customer.get.addressId must be(1)
      customer.get.email must be("max@mustermann.de")
      customer.get.name must be("Test Customer 1")
      customer.get.phone must be("01234")
    }
    "get all Customers" in {
      val customers = test.getCustomers
      customers.size must be(1)
      customers(0).addressId must be(1)
      customers(0).email must be("max@mustermann.de")
      customers(0).name must be("Test Customer 1")
      customers(0).phone must be("01234")
    }
  }
}
