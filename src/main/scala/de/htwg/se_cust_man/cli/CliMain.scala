package de.htwg.se_cust_man.cli

import de.htwg.se_cust_man.controllers.SessionController

import scala.io.StdIn

def signInDialog(sessionController: SessionController) : Boolean = {
  println("Please enter your username:")
  val username = StdIn.readLine(">>>")
  println("Please enter your password:")
  val password = StdIn.readLine(">>>")

  sessionController.signIn(username, password)
}

@main
def runCli(): Unit = {
  val sessionController = new SessionController()
  val mainWindow = new MainWindow(sessionController)
  while (mainWindow.getIsRunning) {
    if (!mainWindow.getIsSignedIn) {
      if (!signInDialog(sessionController)) {
        println("Wrong username or password!")
      }
    }
    else mainWindow.update()
  }
  println("Bye!")
}