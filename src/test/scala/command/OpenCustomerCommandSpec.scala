package command

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import de.htwg.scm.model.Customer
import de.htwg.scm.state.CustomerState
import de.htwg.scm.command.OpenCustomerCommand

class OpenCustomerCommandSpec extends AnyFlatSpec with Matchers {

  val initialState = CustomerState()
  val customer = Customer(1, "John Doe", "john.doe@example.com", "123 Main St", "555-1234")

  "An OpenCustomerCommand" should "set the customer in the state and return true when executed" in {
    val command = OpenCustomerCommand(initialState, customer)

    val result = command.execute()

    result should be(true)

    val currentState = initialState.get
    currentState should be(Some(customer))
  }

  it should "set the previous customer in the state and return true when undone" in {
    val command = OpenCustomerCommand(initialState, customer)

    command.execute()

    val result = command.undo()

    result should be(true)

    val currentState = initialState.get
    currentState should be(None)
  }

  it should "set the customer in the state and return true when redone" in {
    val command = OpenCustomerCommand(initialState, customer)

    command.execute()
    command.undo()

    val result = command.redo()

    result should be(true)

    val currentState = initialState.get
    currentState should be(Some(customer))
  }
}
