package de.htwg.scm
package model

case class Customer(id: Int, name: String, email: String, phone: String, address: String)

object Customer {
  def empty: Customer = Customer(-1, "", "", "", "")

  def apply(args: String): Customer = {
    val idReg = """-i\s+(\d+)""".r
    val nameReg = """-n\s+([^-]*)""".r
    val emailReg = """-e\s+([^-]*)""".r
    val phoneReg = """-p\s+([^-]*)""".r
    val addressReg = """-a\s+([^-]*)""".r

    var id: Option[Int] = None
    var name: Option[String] = None
    var email: Option[String] = None
    var phone: Option[String] = None
    var address: Option[String] = None

    // Extract values from command line arguments
    idReg.findFirstMatchIn(args).foreach(m => id = Some(m.group(1).toInt))
    nameReg.findFirstMatchIn(args).foreach(m => name = Some(m.group(1)))
    emailReg.findFirstMatchIn(args).foreach(m => email = Some(m.group(1)))
    phoneReg.findFirstMatchIn(args).foreach(m => phone = Some(m.group(1)))
    addressReg.findFirstMatchIn(args).foreach(m => address = Some(m.group(1)))

    Customer(id.getOrElse(-1), name.map(_.trim).getOrElse(""), email.map(_.trim).getOrElse(""),
      phone.map(_.trim).getOrElse(""), address.map(_.trim).getOrElse(""))
  }
}

trait ICustomerBuilder {
  def setId(id: Int): ICustomerBuilder
  def setName(name: String): ICustomerBuilder
  def setEmail(email: String): ICustomerBuilder
  def setPhoneNumber(phone: String): ICustomerBuilder
  def setAddress(address: String): ICustomerBuilder
  def build(): Customer
}

class CustomerBuilder extends ICustomerBuilder {
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