package de.htwg.scm
package command

import model.{Customer, Task}
import state.{CustomerState, TaskState}

case class OpenCustomerCommand(state: CustomerState, customer: Customer) extends ICommand {
  private val prevCustomer: Option[Customer] = state.get

  override def execute(): Boolean = {
    state.set(Some(customer))
    true
  }

  override def undo(): Boolean = {
    state.set(prevCustomer)
    true
  }

  override def redo(): Boolean = {
    execute()
  }
}
