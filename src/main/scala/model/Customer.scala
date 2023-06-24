package de.htwg.scm
package model

case class Customer(id: Int, name: String, email: String, phone: String, address: String)
object Customer {
  def empty: Customer = Customer(-1, "", "", "", "")
}

class CustomerBuilder{
   private var id: Int = -1
  private var name: String = ""
  private var email: String = ""
  private var phone: String = ""
  private var address: String = ""

  // constructor with Customer
  def this(customer: Customer) = {
    this()
    this.id = customer.id
    this.name = customer.name
    this.email = customer.email
    this.phone = customer.phone
    this.address = customer.address
  }

  def setId(id: Int): CustomerBuilder = {
    this.id = id
    this
  }

  def setName(name: String): CustomerBuilder = {
    this.name = name
    this
  }

  def setEmail(email: String): CustomerBuilder = {
    this.email = email
    this
  }

  def setPhoneNumber(phone: String): CustomerBuilder = {
    this.phone = phone
    this
  }

  def setAddress(address: String): CustomerBuilder = {
    this.address = address
    this
  }

  def build(): Customer = {
    Customer(id, name, email, phone, address)
  }
}