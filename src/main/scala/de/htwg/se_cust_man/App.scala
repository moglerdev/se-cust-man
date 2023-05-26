package de.htwg.se_cust_man

import scala.::
import scala.util.{Failure, Success, Try}


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

object App {
  @main
  def runCli(): Unit = {
    val app = new App()
    app.runner()
  }
}