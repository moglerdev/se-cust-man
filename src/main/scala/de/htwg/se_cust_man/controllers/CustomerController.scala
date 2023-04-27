package de.htwg.se_cust_man.controllers

import de.htwg.se_cust_man.Subject
import de.htwg.se_cust_man.models.Customer

class CustomerController extends Subject {
  private var customers: Vector[Customer] = Vector.empty

  def getCustomers(): Vector[Customer] = this.customers

  def addCustomer(customer: Customer): Unit = {
    this.customers = this.customers :+ customer
    this.notifyObservers()
  }

  def removeCustomer(customer: Customer) : Unit = {
    this.customers = this.customers.filterNot(_ == customer)
    this.notifyObservers()
  }

  def getCustomer: Vector[Customer] = this.customers

  def getCustomerByName(name: String): Option[Customer] = {
    this.customers.find(_.name == name)
  }
}
