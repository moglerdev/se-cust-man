package de.htwg.se_cust_man

import scala.::
import scala.util.{Failure, Success, Try}


case class CustomerEditor(customer: Customer);



class CommandHistory() {
  var history: Vector[CustomerCommand] = Vector()

  def add(command: CustomerCommand): Unit = {
    history = history.appended(command)
  }

  def undo(): Option[CustomerCommand] = {
    if (history.nonEmpty) {
      while(!history.head.hasBackup) {
        history = history.tail
      }
      Some(history.head)
    } else None
  }

  def printHistory(): Unit = {
    history.foreach(println)
  }
}

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

class App {
  val commandHistory = new CommandHistory()
  val undoHistory = new CommandHistory()
  val customerController = new CustomerController()

  def executeCommand(command: CustomerCommand): Unit = {
    command.execute()
    commandHistory.add(command)
  }

  def undo(): Unit = {
    val command = commandHistory.undo()
    if (command.isDefined) {
      undoHistory.add(command.get)
    }
  }

  def redo(): Unit = {
    customerController.setCustomers(undoHistory.undo().get.backup.get)
  }

  def printHistory(): Unit = {
    commandHistory.printHistory()
  }

  val handlers = new CustomerCommandHandler("add", (customer) => {
    executeCommand(new AddCustomerCommand(this, customerController))
    customerController.addCustomer(customer)
  });

  handlers
    .setNext(new CustomerCommandHandler("update", (customer) => {
      executeCommand(new UpdateCustomerCommand(this, customerController))
      customerController.updateCustomer(customer)
    }))
    .setNext(new CustomerCommandHandler("delete", (customer) => {
      executeCommand(new DeleteCustomerCommand(this, customerController))
      customerController.removeCustomer(customer)
    }))
    .setNext(new CustomerCommandHandler("undo", (customer) => {
      executeCommand(new UndoCustomerCommand(this, customerController))
      customerController.setCustomers(commandHistory.undo().get.backup.get)
    }))
    .setNext(new CustomerCommandHandler("redo", (customer) => {
      executeCommand(new RedoCustomerCommand(this, customerController))
      customerController.setCustomers(undoHistory.undo().get.backup.get)
    }))
    .setNext(new CustomerCommandHandler("history", (customer) => {
      executeCommand(new HistoryCustomerCommand(this, customerController))
    }))
    .setNext(new ExitHandler("exit"))
    .setNext(new CustomerCommandHandler("printall", (customer) => {
      executeCommand(new PrintCustomerCommand(this, customerController))
      customerController.print()
    }))
    .setNext(new CustomerCommandHandler("print", (customer) => {
      executeCommand(new PrintCustomerCommand(this, customerController))
      customerController.print(customer.name)
    }))

  def runner() : Unit = {
    while (true) {
      print("Enter command: ")
      val request = scala.io.StdIn.readLine()
      handlers.handle(request)
    }
  }
}




case class Customer(name: String, address: String, email: String, phone: String)

abstract class CustomerCommand(val app: App, val ctrl: CustomerController) {
  var backup: Option[Vector[Customer]] = None

  def hasBackup: Boolean = backup.isDefined

  def saveBackup(): Unit = {
    backup = Some(ctrl.getCustomers)
  }

  def undo(): Try[Vector[Customer]] = {
    if (backup.isDefined) {
      Success(backup.get)
    } else {
      Failure(new Exception("No backup found"))
    }
  }

  def execute(): Unit
}

class AddCustomerCommand(app: App, ctrl: CustomerController) extends CustomerCommand(app, ctrl) {
  override def execute(): Unit = {
    super.saveBackup()

  }
}

class UpdateCustomerCommand(app: App, ctrl: CustomerController) extends CustomerCommand(app, ctrl) {
  override def execute(): Unit = {
    super.saveBackup()
  }
}

class DeleteCustomerCommand(app: App, ctrl: CustomerController) extends CustomerCommand(app, ctrl) {
  override def execute(): Unit = {
    super.saveBackup()
  }
}

class UndoCustomerCommand(app: App, ctrl: CustomerController) extends CustomerCommand(app, ctrl) {
  override def execute(): Unit = {
    app.undo()
  }
}

class RedoCustomerCommand(app: App, ctrl: CustomerController) extends CustomerCommand(app, ctrl) {
  override def execute(): Unit = {
    app.redo()
  }
}

class PrintCustomerCommand(app: App, ctrl: CustomerController) extends CustomerCommand(app, ctrl) {
  override def execute(): Unit = {
    ctrl.print()
  }
}

class HistoryCustomerCommand(app: App, ctrl: CustomerController) extends CustomerCommand(app, ctrl) {
  override def execute(): Unit = {
    println("HistoryCommand")
  }
}




abstract class Handler {
  var next : Option[Handler] = None

  def setNext(next: Handler) : Handler = {
    this.next = Some(next)
    next
  }

  def handle(request: String): Try[Boolean] = {
    if (next.isDefined) {
      next.get.handle(request)
    } else {
      Failure(new Exception("No Handler found for request: " + request))
    }
  }
}

class CustomerCommandHandler(key: String, call: (customer: Customer) => Unit) extends Handler {
  override def handle(request: String): Try[Boolean] = {
    if (request.trim().startsWith(key)) {
      val args = request.trim().split(" ")
      if (args.length != 5) return Failure(new Exception("Invalid number of arguments: " + args.length + " expected 5"))
      call(Customer(args(1), args(2), args(3), args(4)))
      Success(true)
    } else {
      super.handle(request)
    }
  }
}

class HistoryHandler(key: String) extends Handler {
  override def handle(request: String): Try[Boolean] = {
    if (request.trim().startsWith(key)) {
      Success(true)
    } else {
      super.handle(request)
    }
  }
}

class PrintCustomerHandler(key: String, name: String) extends Handler {
  override def handle(request: String): Try[Boolean] = {
    if (request.trim().startsWith(key)) {
      Success(true)
    } else {
      super.handle(request)
    }
  }
}

class ExitHandler(key: String) extends Handler {
  override def handle(request: String): Try[Boolean] = {
    if (request.trim().startsWith(key)) {
      System.exit(0)
      Try(true)
    } else {
      super.handle(request)
    }
  }
}




object App {
  @main
  def runCli(): Unit = {
    val app = new App()
    app.runner()
  }
}