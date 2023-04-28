package de.htwg.se_cust_man.controllers

import de.htwg.se_cust_man.{Subject, models}
import de.htwg.se_cust_man.models.Customer


class CustomerController(var values: Vector[Customer]) extends Subject {

  def getCustomers : Vector[Customer] = {
    values
  }

  def removeCustomer(customer: Customer) : Boolean = {
    values = values.filterNot(_ == customer)
    this.notifyObservers()
    true
  }

  def getCustomerById(id: Long): Option[Customer] = {
    values.find(_.id == id)
  }

  def getCustomerByName(name: String): Option[Customer] = {
    values.find(_.name == name)
  }
}
