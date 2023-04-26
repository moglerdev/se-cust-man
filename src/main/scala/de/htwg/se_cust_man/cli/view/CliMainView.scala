package de.htwg.se_cust_man.cli.view

import de.htwg.se_cust_man.controllers.UserController
import de.htwg.se_cust_man.models.User

class CliMainView extends CliView {
  val cltr = new UserController
  cltr.subscribe(this)

  var user: Option[User] = cltr.session

  override def update(): Unit = {
    user = cltr.session
  }

  def printHelp(): Unit = {
    write("Available commands:")
    write("  ls - list all customers")
    write("  mk <customer> <name> - create a new customer")
    write("  rm customer <id> - remove a customer")
    write("  cd <customer_id> - open a customer")
    write("  logout - logout from the program")
    write("  exit - exit the program")
  }

  def eval() : Unit = {
    val input = prompt(user.get.username)
    val args = input.split(" ")
    args(0) match {
      case "help" => printHelp()
      case "exit" => System.exit(0)
      case _ => write("Unknown command")
    }
  }

  override def render(): Unit = {
    val signInView = new CliSignInView(cltr)
    signInView.render()
    signInView.close()

    write("Hello " + user.get.username)
    write("write 'help' if you get stuck. \n\n")
    while(true) {
      eval()
    }
  }

  override def close(): Unit = {
    cltr.unsubscribe(this)
  }
}
