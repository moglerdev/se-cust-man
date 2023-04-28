package de.htwg.se_cust_man.models

import de.htwg.se_cust_man.models
import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec

class CustomerSpec extends AnyWordSpec with Matchers {
  "customer helper" should {
    "convert to csv" in {
      val customer = Customer(1, "Name", "Address", "Phone", "Email")
      customer.toCSV must be("1;Name;Address;Phone;Email")
    }
    "from csv to model" in {
      val customer = Customer(1, "Name", "Address", "Phone", "Email")
      Customer.fromCSV("1;Name;Address;Phone;Email") must be(customer)
      val customer2 = Customer(1, "Name", "", "", "")
      Customer.fromCSV("1;Name;") must be(customer2)
    }
    "toString" in {
      val customer = Customer(1, "Name", "Address", "Phone", "Email")
      customer.toString must be("Customer(1,Name,Address,Phone,Email)")
    }
  }
}
