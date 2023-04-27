package de.htwg.se_cust_man.cli

import de.htwg.se_cust_man
import de.htwg.se_cust_man.Observer
import de.htwg.se_cust_man.controllers.{CustomerController, SessionController}

import scala.io.StdIn

class MainWindow(sessionController: SessionController) extends Observer {
  val customerController: CustomerController = new CustomerController

  sessionController.subscribe(this)
  customerController.subscribe(this)

  private var isRunning = true
  private var isSignedIn = false

  def getIsRunning: Boolean = isRunning
  def getIsSignedIn : Boolean = isSignedIn

  override def onNotify(): Unit = {
    isSignedIn = sessionController.isSignedIn()
  }

  def update() : Unit = {
    val input = StdIn.readLine(sessionController.getUsername()).split(" ").toVector
    val args = input.tail
    input.head match
      case "logout" => {
        sessionController.signOut()
      }
      case "exit" => {
        isRunning = false
      }
      case "create" => {
        if (args.length == 1) {
          // TODO: create customer
        } else {
          println("Wrong number of arguments")
        }
      }
  }
}
