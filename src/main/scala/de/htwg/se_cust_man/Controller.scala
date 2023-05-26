package de.htwg.se_cust_man


trait Observer {
  def update(): Unit
}

abstract class Subject {
  var observers: List[Observer] = List()

  def registerObserver(observer: Observer): Unit = {
    observers = observer :: observers
  }
  def removeObserver(observer: Observer): Unit = {
    observers = observers.filterNot(_ == observer)
  }
  def notifyObservers(): Unit = {
    observers.foreach(_.update())
  }
}

case class CustomerController() extends Subject {
  var customers: Vector[Customer] = Vector()

  def addCustomer(customer: Customer): Unit = {
    customers = customers.appended(customer)
    notifyObservers()
  }

  def removeCustomer(customer: Customer): Unit = {
    customers = customers.filterNot(_ == customer)
    notifyObservers()
  }

  def updateCustomer(customer: Customer): Unit = {
    customers = customers.map(c => if (c == customer) customer else c)
    notifyObservers()
  }

  def getCustomers: Vector[Customer] = {
    customers
  }

  def print(): Unit = {
    customers.foreach(println)
  }

  def print(name: String): Unit = {
    println(customers.find(_.name == name))
  }

  def setCustomers(customers: Vector[Customer]): Unit = {
    this.customers = customers
    notifyObservers()
  }
}