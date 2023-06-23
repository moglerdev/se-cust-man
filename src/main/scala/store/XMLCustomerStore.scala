package de.htwg.scm
package store

import de.htwg.scm.models.Customer

import java.io.{File, PrintWriter}
import scala.xml.XML

class XMLCustomerStore extends IStore[Customer] {
  private val xmlFilePath: String = "store/customers.xml"

  override def create(model: Customer): Int = {
    val newModel = model.copy(id = getLastId + 1)
    val customers = loadCustomersFromXML()
    val updatedCustomers = customers :+ newModel
    saveCustomersToXML(updatedCustomers)
    newModel.id
  }

  override def read(id: Int): Option[Customer] = {
    val customers = loadCustomersFromXML()
    customers.find(_.id == id)
  }

  override def update(id: Int, model: Customer): Int = {
    val customers = loadCustomersFromXML()
    val updatedCustomers = customers.map(c => if (c.id == id) model else c)
    saveCustomersToXML(updatedCustomers)
    id
  }

  override def delete(id: Int): Int = {
    val customers = loadCustomersFromXML()
    val updatedCustomers = customers.filterNot(_.id == id)
    saveCustomersToXML(updatedCustomers)
    id
  }

  override def getAll: List[Customer] = {
    loadCustomersFromXML()
  }

  override def getLastId: Int = {
    val customers = loadCustomersFromXML()
    if (customers.nonEmpty)
      customers.map(_.id).max
    else
      -1
  }

  private def loadCustomersFromXML(): List[Customer] = {
    val file = new File(xmlFilePath)
    if (file.exists()) {
      val xml = XML.loadFile(file)
      (xml \ "customer").map(parseCustomerFromXML).toList
    } else {
      List.empty
    }
  }

  private def parseCustomerFromXML(node: scala.xml.Node): Customer = {
    val id = (node \ "@id").text.toInt
    val name = (node \ "name").text
    val email = (node \ "email").text
    val address = (node \ "address").text
    val phone = (node \ "phone").text
    Customer(id, name, email, address, phone)
  }

  private def saveCustomersToXML(customers: List[Customer]): Unit = {
    val xml =
      <customers>
        {customers.map(customerToXML)}
      </customers>
    val pw = new PrintWriter(new File(xmlFilePath))
    pw.write(xml.toString())
    pw.close()
  }

  private def customerToXML(customer: Customer): scala.xml.Node = {
    <customer id={customer.id.toString}>
      <name>{customer.name}</name>
      <email>{customer.email}</email>
      <address>{customer.address}</address>
      <phone>{customer.phone}</phone>
    </customer>
  }
}
