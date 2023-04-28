package de.htwg.se_cust_man.controllers

import de.htwg.se_cust_man.Subject
import de.htwg.se_cust_man.cli.Cli
import de.htwg.se_cust_man.models.{Customer, Project}
import de.htwg.se_cust_man.controllers.DBCustomers

class EditorCustomerController extends Subject{
  private var openCustomer: Option[Customer] = None
  private var isNew: Boolean = false

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
      DBCustomers.add(cust)
      Cli.send(s"NEW:CUSTOMER:${cust.toString}")
    } else {
      DBCustomers.value = DBCustomers.value.map(x => {
        if (x.id == cust.id) cust
        else x
      })
      Cli.send(s"UPDATE:CUSTOMER:${cust.toString}")
    }
    if (closeAfter) {
      closeCustomer()
    }
    true
  }

  def createCustomer(): Option[Customer] = {
    val res = openCustomer
    openCustomer = Some(Customer(-1, "", "", "", ""))
    isNew = true
    notifyObservers()
    res
  }

  def openCustomer(id: Int): Option[Customer] = {
    isNew = false
    openCustomer = DBCustomers.value.find(x => x.id == id)
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
