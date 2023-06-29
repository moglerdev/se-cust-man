package de.htwg.scm
package command

import model.Customer
import state.CustomerState
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class SetCustomerCommandSpec extends AnyWordSpec with Matchers {
  "A SetCustomerCommand" when {
    val initialState: Option[Customer] = Some(Customer(1, "John Doe", "johndoe@example.com", "1234567890", "123 Street"))
    val state: CustomerState = new CustomerState
    state.set(initialState)
    val command: SetCustomerCommand = SetCustomerCommand(state, Some("Jane Smith"), Some("janesmith@example.com"), None, Some("456 Avenue"))

    "executed" should {
      "set the specified customer properties" in {
        command.execute() should be(true)
        state.get should be(Customer(1, "Jane Smith", "janesmith@example.com", "1234567890", "456 Avenue"))
      }
    }

    "undone" should {
      "revert the customer state to its original value" in {
        command.undo() should be(true)
        state.get should be(initialState.get)
      }
    }

    "redone" should {
      "execute the command again and set the specified customer properties" in {
        command.redo() should be(true)
        state.get should be(Customer(1, "Jane Smith", "janesmith@example.com", "1234567890", "456 Avenue"))
      }
    }
  }
}
