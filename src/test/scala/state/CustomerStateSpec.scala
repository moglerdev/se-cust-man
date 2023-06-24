package state

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import de.htwg.scm.model.Customer
import de.htwg.scm.state.CustomerState

class CustomerStateSpec extends AnyFlatSpec with Matchers {

  "A CustomerState" should "initially have no customer" in {
    val customerState = new CustomerState()

    customerState.get should be(None)
  }

  it should "set and retrieve a customer" in {
    val customerState = new CustomerState()
    val customer = Customer(1, "John Doe", "john@example.com", "123456789", "123 Main St")

    customerState.set(Some(customer))

    customerState.get should be(Some(customer))
  }

  it should "allow setting the customer to None" in {
    val customerState = new CustomerState()
    val customer = Customer(1, "John Doe", "john@example.com", "123456789", "123 Main St")

    customerState.set(Some(customer))
    customerState.set(None)

    customerState.get should be(None)
  }
}
