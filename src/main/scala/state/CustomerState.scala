package de.htwg.scm
package state

import model.Customer

import scala.util.Try

class CustomerState extends IState[Customer] {
  private var customer: Option[Customer] = None

  override def set(model: Option[Customer]): CustomerState = {
    customer = model
    this
  }

  override def get: Customer = customer.get

  override def option: Option[Customer] = customer

  override def isDefined: Boolean = customer.isDefined

  override def isEmpty: Boolean = customer.isEmpty
}
