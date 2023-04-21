package de.htwg.se_cust_man.tui

import de.htwg.se_cust_man.models.Customer

class IOInvoiceDir(name: String, parent: Option[Directory], children: List[File])
  extends Directory(name, parent, children) {


  override def toString: String = {
    "(Invoice) " + name + "/"
  }
}


object IOCustomerDir {
  def makeProjectDir(input: Input, dir: IOCustomerDir): Executed = {
    val pd = new IOProjectDir(input.args(1), Some(dir))
    Executed(input, ExecutedResult.Success, Some(dir), Some("Project created"))
  }

  def makeInvoice(input: Input, dir: IOCustomerDir): Executed = {
    Executed(input, ExecutedResult.Success, Some(dir), Some("Invoice created"))
  }
}


/**
 * Class for Customer Dir
 * @param customer Customer
 * @param parent Parent Directory
 * @param children List of Children (IOInvoiceDir | IOProjectDir)
 */
class IOCustomerDir(customer: Customer, parent: Option[Directory], children: List[IOInvoiceDir | IOProjectDir])
  extends Directory(customer.name.trim().replace(" ", "_"), parent, children) {


  /**
   * Constructor for IOCustomerDir
   * @param customer Customer
   * @param parent Parent Directory
   */
  def this(customer: Customer, parent: Option[Directory]) = this(customer, parent, List())

  /**
   * Constructor for IOCustomerDir
   * @param name Customer
   */
  def this(customer: Customer) = this(customer, None, List())

  override def execute(input: Input): Option[Executed] = {
    if (input.cmd == "cd") Some(Directory.changeDir(input, this))
    else if (input.cmd == "ls") Some(Directory.listDir(input, this))
    else if (input.cmd == "make") {
      if (input.args.length < 2) return Some(Executed(input, ExecutedResult.Failure, Some(this), Some("Not enough arguments, expected: make <project | invoice> <name>")))
      input.args(0) match {
        case "project" => Some(IOCustomerDir.makeProjectDir(input, this))
        case "invoice" => Some(IOCustomerDir.makeInvoice(input, this))
        case _ => Some(Executed(input, ExecutedResult.Failure, Some(this), Some("Unknown argument, expected: make <project | invoice> <name>")))
      }
    } else if (input.cmd == "print") {
      // print customer information
      val sb = new StringBuilder()
      sb.append("Customer: " + customer.name + "\n")
      sb.append("Address: " + customer.address + "\n")
      sb.append("Phone: " + customer.phone + "\n")
      sb.append("Email: " + customer.email + "\n")
      //sb.append("Website: " + customer.website + "\n")
      //sb.append("Notes: " + customer.notes + "\n")
      sb.deleteCharAt(sb.length - 1)
      Some(Executed(input, ExecutedResult.Success, Some(this), Some(sb.toString())))
    }
    else None
  }

  override def toString: String = {
    "(Customer) " + customer.name + "/"
  }
}