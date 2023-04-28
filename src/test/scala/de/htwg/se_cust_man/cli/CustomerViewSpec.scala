package de.htwg.se_cust_man.cli

import de.htwg.se_cust_man.cli.view.CustomerView
import de.htwg.se_cust_man.controllers.EditorCustomerController
import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec
import de.htwg.se_cust_man.models.Customer

class TestMockController extends EditorCustomerController(Vector.empty) {
  override def isNewCustomer: Boolean = true
  override def closeCustomer(): Option[Customer] = {
    Some(Customer.empty)
  }

  override def createCustomer(customer: Option[Customer]): Option[Customer] = {
    Some(Customer.empty)
  }

  override def updateValues(key: String, value: String): Boolean = {
    true
  }

  override def getOpenCustomer: Option[Customer] = {
    Some(Customer.empty)
  }
}

class CustomerViewSpec extends AnyWordSpec with Matchers {
  "Customer" should {
    val editor = TestMockController()
    val customer :CustomerView = new CustomerView(editor)
    "prompt" in {
      editor.createCustomer()
      editor.updateValues("name", "test")
      customer.promptPrefix must be("(NEW CUSTOMER)")
    }

    "eval" in {

      customer.eval(Input("exit", Vector.empty)) must be(Some("Customer closed with out saves"))
      customer.eval(Input("save", Vector.empty)) must be(Some("Customer is saved and closed"))
      customer.eval(Input("set", Vector.empty)) must be(Some("Value could not be set"))
      customer.eval(Input("set", Vector("name", "ok"))) must be(Some("Values updated"))

    }
  }
}
