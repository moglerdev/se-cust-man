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

  override def get: Option[Customer] = customer
}
