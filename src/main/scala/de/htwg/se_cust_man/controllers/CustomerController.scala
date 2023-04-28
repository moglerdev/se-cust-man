package de.htwg.se_cust_man.controllers

import de.htwg.se_cust_man.{Subject, models}
import de.htwg.se_cust_man.models.Customer

object DBCustomers {
  def add(customer: Customer): Int = {
    val newId = DBCustomers.value.length
    DBCustomers.value = DBCustomers.value :+ Customer(id = newId,
      customer.name, customer.address, customer.phone, customer.email)
    newId
  }

  var value : Vector[Customer] = Vector(
    Customer(0, "Christopher Jaeger", "78166 Donaueschingen, Bregstr. 20", "+49 7836 2036", "jockel@example.de"),
    Customer(1, "Dennis Schulze", "72343 Musterstadt, Musterstr. 1", "+49 123 33333", "max@musterman.de")
  )
}

class CustomerController extends Subject {

  def getCustomers : Vector[Customer] = {
    DBCustomers.value
  }

  def removeCustomer(customer: Customer) : Boolean = {
    DBCustomers.value = DBCustomers.value.filterNot(_ == customer)
    this.notifyObservers()
    true
  }

  def getCustomerByName(name: String): Option[Customer] = {
    DBCustomers.value.find(_.name == name)
  }
}
