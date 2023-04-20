package de.htwg.se_cust_man.tui


class IOInvoiceDir(name: String, parent: Option[Directory], children: List[File])
  extends Directory(name, parent, children) {
}


object IOCustomerDir {
  def makeProjectDir(input: Input, dir: IOCustomerDir): Executed = {
    val pd = new IOProjectDir(input.args(1), Some(dir))
    Executed(input, ExecutedResult.Success, Some(dir), Some("Project created"))
  }

  def makeInvoice(input: Input, dir: IOCustomerDir): Executed = {
    val pd = new IOProjectDir(input.args(1), Some(dir))
    Executed(input, ExecutedResult.Success, Some(dir), Some("Project created"))
  }
}


/**
 * Class for Customer Dir
 * @param name Name of the Customer
 * @param parent Parent Directory
 * @param children List of Children (IOInvoiceDir | IOProjectDir)
 */
class IOCustomerDir(name: String, parent: Option[Directory], children: List[IOInvoiceDir | IOProjectDir])
  extends Directory(name, parent, children) {


  /**
   * Constructor for IOCustomerDir
   * @param name Name of the Directory
   * @param parent Parent Directory
   */
  def this(name: String, parent: Option[Directory]) = this(name, parent, List())

  /**
   * Constructor for IOCustomerDir
   * @param name Name of the Directory
   */
  def this(name: String) = this(name, None, List())

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
    }
    else None
  }
}