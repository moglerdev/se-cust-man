package de.htwg.se_cust_man.cli.view

import de.htwg.se_cust_man.Observer
import de.htwg.se_cust_man.cli.Input
import de.htwg.se_cust_man.controllers.EditorCustomerController
import de.htwg.se_cust_man.models.Customer

class CustomerView(editor: EditorCustomerController) extends CliView {
  editor.subscribe(this)

  override def promptPrefix: String = {
    val prefix = if (changes) "[!C!]" else ""
    if(editor.isNewCustomer) prefix + "(NEW CUSTOMER)"
    else prefix + editor.getOpenCustomer.get.name
  }

  private var changes = false
  private var open = false
  override def isOpen: Boolean = open

  override def onNotify(): Unit = {
    open = editor.isOpen
  }

  private def updateValue(input: Input): Boolean = {
    if(input.arguments.length < 2) return false

    if (editor.updateValues(input.arguments.head, input.arguments.tail.mkString(" "))) {
      true
    } else {
      false
    }
  }

  override def eval(input: Input): Option[String] = {
    input.command match
      case "exit" =>
        editor.closeCustomer()
        Some("Customer closed with out saves")
      case "save" =>
        editor.saveCustomer(true)
        changes = false
        Some("Customer is saved and closed")
      case "print" =>
        Some(editor.getOpenCustomer.get.toString)
      case "set" =>
        if(updateValue(input))
          changes = true
          Some("Values updated")
        else Some("Value could not be set")
      case _ => None
  }

  override def close(): Unit = {
    editor.unsubscribe(this)
  }
}
