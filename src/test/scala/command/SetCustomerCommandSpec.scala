package command

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

package de.htwg.scm.command

import model.Customer
import state.CustomerState

class SetCustomerCommandSpec extends AnyFlatSpec with Matchers {

  "A SetCustomerCommand" should "execute successfully and set the customer properties" in {
    // Create a test CustomerState
    val initialState = CustomerState(Some(Customer(1, "John", "john@example.com", "123456789", "123 Main St")))
    val customerState = initialState.copy() // Make a copy to preserve the initial state

    // Create a SetCustomerCommand with new property values
    val command = SetCustomerCommand(customerState, Some("Alice"), Some("alice@example.com"), None, Some("456 Second St"))

    // Execute the command
    val executeResult = command.execute()

    // Verify that the command executed successfully
    executeResult should be(true)

    // Verify that the customer properties were set correctly
    val updatedCustomer = customerState.get.getOrElse(Customer(-1, "", "", "", ""))
    updatedCustomer.name should be("Alice")
    updatedCustomer.email should be("alice@example.com")
    updatedCustomer.phone should be("123456789") // Phone property wasn't modified
    updatedCustomer.address should be("456 Second St")
  }

  it should "undo the command and restore the initial customer state" in {
    // Create a test CustomerState
    val initialState = CustomerState(Some(Customer(1, "John", "john@example.com", "123456789", "123 Main St")))
    val customerState = initialState.copy() // Make a copy to preserve the initial state

    // Create a SetCustomerCommand with new property values
    val command = SetCustomerCommand(customerState, Some("Alice"), Some("alice@example.com"), None, Some("456 Second St"))

    // Execute the command
    command.execute()

    // Undo the command
    val undoResult = command.undo()

    // Verify that the command was undone successfully
    undoResult should be(true)

    // Verify that the customer state was restored to its initial value
    val restoredCustomer = customerState.get.getOrElse(Customer(-1, "", "", "", ""))
    restoredCustomer should be(initialState.get.getOrElse(Customer(-1, "", "", "", "")))
  }

  it should "redo the command and set the customer properties again" in {
    // Create a test CustomerState
    val initialState = CustomerState(Some(Customer(1, "John", "john@example.com", "123456789", "123 Main St")))
    val customerState = initialState.copy() // Make a copy to preserve the initial state

    // Create a SetCustomerCommand with new property values
    val command = SetCustomerCommand(customerState, Some("Alice"), Some("alice@example.com"), None, Some("456 Second St"))

    // Execute the command
    command.execute()

    // Undo the command
    command.undo()

    // Redo the command
    val redoResult = command.redo()

    // Verify that the command was redone successfully
    redoResult should be(true)

    // Verify that the customer properties were set again
    val updatedCustomer = customerState.get.getOrElse(Customer(-1, "", "", "", ""))
    updatedCustomer.name should be("Alice")
    updatedCustomer.email should be("alice@example.com")
    updatedCustomer.phone should be("123456789") // Phone property wasn't modified
    updatedCustomer.address should be("456 Second St")
  }
}
