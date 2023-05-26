package de.htwg.se_cust_man

import scala.::
import scala.util.{Failure, Success, Try}

import de.htwg.se_cust_man.{RedoCustomerCommand, UndoCustomerCommand}

class App {
  val commandHistory = new CommandHistory()
  val undoHistory = new CommandHistory()
  val customerController = new CustomerController()

  def executeCommand(command: CustomerCommand): Unit = {
    if (command.execute())
      if (command.isInstanceOf[UndoCustomerCommand])
        undoHistory.push(command)
      else
        commandHistory.push(command)

    commandHistory.printHistory()
  }

  def undo(): Boolean = {
    val command = commandHistory.pop()
    if (command.isDefined) {
      customerController.setCustomers(command.get.backup.get)
      true
    }
    else false
  }

  def redo(): Boolean = {
    val command = undoHistory.pop()
    if (command.isDefined) {
      customerController.setCustomers(command.get.backup.get)
      true
    }
    else false
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
    .setNext(new UndoHandler("undo", () => {
      executeCommand(new UndoCustomerCommand(this, customerController))
    }))
    .setNext(new RedoHandler("redo", () => {
      executeCommand(new RedoCustomerCommand(this, customerController))
    }))
    // .setNext(new HistoryHandler("history", (customer) => {
    //   // executeCommand(new HistoryCustomerCommand(this, customerController))
    // }))
    .setNext(new ExitHandler("exit"))
    .setNext(new PrintCustomerHandler("printall", (customer) => {
      customerController.print();
    }))
    .setNext(new PrintCustomerHandler("print", (name) => {
      customerController.print(name)
    }))

  def runner() : Unit = {
    while (true) {
      print("Enter command: ")
      val request = scala.io.StdIn.readLine()
      handlers.handle(request) match
        case Failure(exception) => println(exception.getMessage)
        case Success(value) => println(value)
      
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
