package de.htwg.se_cust_man.cli.view

import de.htwg.se_cust_man.Observer
import de.htwg.se_cust_man.controllers.UserController
import de.htwg.se_cust_man.models.User

class CliSignInView(cltr: UserController) extends CliView {
  override def onNotify(): Unit = {
  }

  def requireSignIn(): Boolean = {
    val username = prompt("username")
    val password = prompt("password")
    cltr.signIn(username, password)
  }

  def render(): Unit = {
    write("You need to SignIn First before you can start")
    while(!requireSignIn()) {
      write("SignIn failed, try again")
      write("To exit the program strike Ctrl + D")
    }
    write("SignIn successful")
  }

  def close(): Unit = { }
}
