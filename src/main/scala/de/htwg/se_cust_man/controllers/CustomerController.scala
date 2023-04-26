package de.htwg.se_cust_man.controllers

import de.htwg.se_cust_man.Subject
import de.htwg.se_cust_man.models.Customer

class CustomerController extends Subject {
  private var customer: Vector[Customer] = Vector.empty

  def addCustomer(customer: Customer): Unit = {
    this.customer = this.customer :+ customer
    this.notifyObservers()
  }

  def removeCustomer(customer: Customer) : Unit = {
    this.customer = this.customer.filterNot(_ == customer)
    this.notifyObservers()
  }

  def getCustomer: Vector[Customer] = this.customer

  def getCustomerByName(name: String): Option[Customer] = {
    this.customer.find(_.name == name)
  }
}
