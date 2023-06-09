package de.htwg.scm
package command

import model.Customer
import state.CustomerState

import store.DBCustomerStore

// Command for modifying a customer
case class SetCustomerCommand(state: CustomerState, name: Option[String], email: Option[String], phone: Option[String], address: Option[String]) extends ICommand {
  val customer: Option[Customer] = state.option

  // Executes the command by setting the specified customer properties
  override def execute(): Boolean = {
    val c = customer.getOrElse(Customer(-1, "", "", "", ""))
    val toSet = Customer(
      c.id,
      name.getOrElse(c.name),
      email.getOrElse(c.email),
      phone.getOrElse(c.phone),
      address.getOrElse(c.address)
    )
    state.set(Some(toSet))
    true
  }

  // Undoes the command by reverting the customer state to its original value
  override def undo(): Boolean = {
    state.set(customer)
    true
  }

  // Redoes the command by executing it again
  override def redo(): Boolean = {
    execute()
  }
}
