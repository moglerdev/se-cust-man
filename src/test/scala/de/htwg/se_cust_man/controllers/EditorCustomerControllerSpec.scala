package de.htwg.se_cust_man.controllers

import de.htwg.se_cust_man.models.Customer
import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec

class EditorCustomerControllerSpec extends AnyWordSpec with Matchers {
  "EditorCustomerController" should {
    val default : Vector[Customer] = Vector(Customer(0, "Name", "Address", "Phone", "Email"))
    "incoming message handling" in {
      val controller = new EditorCustomerController(default)
      controller.openCustomer(0)

      val customer = Customer(10, "Name", "Address", "Phone", "Email")
      controller.onMessage(s"CUSTOMER::NEW::${customer.toCSV}")
      controller.openCustomer(10).get must be (customer)
      val updateCustomer = customer.copy(name = "NewName")
      controller.onMessage(s"CUSTOMER::UPDATE::${updateCustomer.toCSV}")
      controller.getOpenCustomer.get.name must be(updateCustomer.name)

      controller.onMessage(s"CUSTOMER::DELETE::${updateCustomer.toCSV}")
    }

    "get open customer" in {
      val controller = new EditorCustomerController(default)
      controller.openCustomer(0).get must be(default.head)
      controller.getOpenCustomer.get.name must be(default.head.name)
      controller.isOpen must be(true)
    }

    "create customer" in {
      val controller = new EditorCustomerController(default)
      val customer = Customer(10, "Name", "Address", "Phone", "Email")
      controller.createCustomer(Some(customer))
      controller.getOpenCustomer.get.name must be(customer.name)
      controller.isOpen must be(true)
      controller.isNewCustomer must be(true)

      controller.createCustomer()
      controller.getOpenCustomer.get.name must be("")
      controller.isOpen must be(true)
      controller.isNewCustomer must be(true)
    }

    "update value" in {
      val controller = new EditorCustomerController(default)
      val customer = Customer(10, "Name", "Address", "Phone", "Email")
      controller.updateValues("name", "NewName") must be(false)

      controller.createCustomer(Some(customer))
      controller.updateValues("name", "NewName") must be (true)
      controller.getOpenCustomer.get.name must be("NewName")
      controller.updateValues("address", "NewName") must be (true)
      controller.getOpenCustomer.get.address must be("NewName")
      controller.updateValues("phone", "NewName") must be (true)
      controller.getOpenCustomer.get.phone must be("NewName")
      controller.updateValues("email", "NewName") must be (true)
      controller.getOpenCustomer.get.email must be("NewName")

      controller.updateValues("spock", "NewName") must be (false)
      controller.getOpenCustomer.get.email must be("NewName")

    }

    "save customer" in {
      val controller = new EditorCustomerController(default)
      val customer = Customer(10, "Name", "Address", "Phone", "Email")
      controller.saveCustomer() must be (false)

      controller.createCustomer(Some(customer))
      controller.updateValues("name", "NewName")
      controller.saveCustomer() must be(true)
      controller.saveCustomer(closeAfter = true) must be(true)

      controller.openCustomer(10)
      controller.isNewCustomer must be(false)
      controller.updateValues("name", "NewName")
      controller.saveCustomer() must be (true)
    }

    "close customer" in {
      val controller = new EditorCustomerController(default)
      val customer = Customer(10, "Name", "Address", "Phone", "Email")
      controller.createCustomer(Some(customer))
      controller.closeCustomer()
      controller.isOpen must be(false)
      controller.isNewCustomer must be(false)
    }
  }
}
