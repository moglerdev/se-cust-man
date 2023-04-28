package de.htwg.se_cust_man.controllers

import de.htwg.se_cust_man.Subject
import de.htwg.se_cust_man.cli.Cli
import de.htwg.se_cust_man.models.{Customer, Project}

class EditorCustomerController(var customers: Vector[Customer]) extends Subject{
  private var openCustomer: Option[Customer] = None
  private var isNew: Boolean = false

  def onMessage(msg: String): Unit = {
    val objs = msg.split("::")
    if (objs.head.startsWith("CUSTOMER")) {
      val r = Customer.fromCSV(objs.last)
      if (openCustomer.isDefined && r.id == openCustomer.get.id) {
        openCustomer = Some(r)
        isNew = false
      }
      objs(1) match
        case "UPDATE" => customers = customers.map(x => {
          if (x.id == r.id) r
          else x
        })
        case "NEW" => customers = customers :+ r
        case "DELETE" => customers = customers.filter(x => x.id != r.id)
        case _ => {}
    }
    notifyObservers()
  }
  // Auslagerb
  //Cli.subscribe(this.onMessage)

  def getOpenCustomer: Option[Customer] = openCustomer

  def isOpen: Boolean = openCustomer.isDefined || isNew
  def isNewCustomer: Boolean = isNew

  def updateValues(key: String, value: String): Boolean = {
    if (!isOpen) return false
    val cus = openCustomer.get
    openCustomer = Some(key match
      case "name" => cus.copy(name = value)
      case "address" => cus.copy(address = value)
      case "phone" => cus.copy(phone = value)
      case "email" => cus.copy(email = value)
      case _ => return false
    )
    notifyObservers()
    true
  }

  def saveCustomer(closeAfter: Boolean = false): Boolean = {
    if (openCustomer.isEmpty) return false
    val cust = openCustomer.get
    if (isNew) {
      customers = customers :+ cust
      Cli.send(s"CUSTOMER::NEW::${cust.toCSV}")
    } else {
      customers = customers.map(x => {
        if (x.id == cust.id) cust
        else x
      })
      Cli.send(s"CUSTOMER::UPDATE::${cust.toCSV}")
    }
    if (closeAfter) {
      closeCustomer()
    }
    true
  }

  def createCustomer(customer: Option[Customer] = None): Option[Customer] = {
    val res = openCustomer
    openCustomer = Some(customer.getOrElse(Customer.empty))
    isNew = true
    notifyObservers()
    res
  }

  def openCustomer(id: Int): Option[Customer] = {
    isNew = false
    openCustomer = customers.find(x => x.id == id)
    notifyObservers()
    openCustomer
  }

  def closeCustomer(): Option[Customer] = {
    isNew = false
    val result = openCustomer
    openCustomer = None
    notifyObservers()
    result
  }
}
