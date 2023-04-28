package de.htwg.se_cust_man.controllers

import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec

import de.htwg.se_cust_man.models.Customer

class CustomerControllerSpec extends AnyWordSpec with Matchers{
  "A CustomerController" should {
    val default : Vector[Customer] = Vector(Customer(1, "Hello", "World", "Was", "Geht"))
    val controller = new CustomerController(default)
    "have a customer" in {
      controller.getCustomers must be(default)
    }
    "have a customer by id" in {
      controller.getCustomerById(1).get.id must be(default.head.id)
    }
    "have a customer by name" in {
      controller.getCustomerByName("Hello").get.name must be(default.head.name)
    }
    "remove a customer" in {
      controller.removeCustomer(default.head) must be(true)
    }
  }
}
