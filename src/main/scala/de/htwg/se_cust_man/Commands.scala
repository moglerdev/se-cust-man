package de.htwg.se_cust_man

import scala.util.{Failure, Success, Try}


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

  def execute(): Boolean
}


class AddCustomerCommand(app: App, ctrl: CustomerController) extends CustomerCommand(app, ctrl) {
  override def execute(): Boolean = {
    super.saveBackup()
    true
  }
}

class UpdateCustomerCommand(app: App, ctrl: CustomerController) extends CustomerCommand(app, ctrl) {
  override def execute(): Boolean = {
    super.saveBackup()
    true
  }
}

class DeleteCustomerCommand(app: App, ctrl: CustomerController) extends CustomerCommand(app, ctrl) {
  override def execute(): Boolean = {
    super.saveBackup()
    true
  }
}

class UndoCustomerCommand(app: App, ctrl: CustomerController) extends CustomerCommand(app, ctrl) {
  override def execute(): Boolean = {
    saveBackup()
    app.undo()
  }
}

class RedoCustomerCommand(app: App, ctrl: CustomerController) extends CustomerCommand(app, ctrl) {
  override def execute(): Boolean = {
    saveBackup()
    app.redo()
  }
}

class PrintCustomerCommand(app: App, ctrl: CustomerController) extends CustomerCommand(app, ctrl) {
  override def execute(): Boolean = {
    saveBackup()
    ctrl.print()
    false
  }
}

class HistoryCustomerCommand(app: App, ctrl: CustomerController) extends CustomerCommand(app, ctrl) {
  override def execute(): Boolean = {
    println("HistoryCommand")
    false
  }
}
