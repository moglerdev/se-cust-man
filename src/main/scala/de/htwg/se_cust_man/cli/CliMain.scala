package de.htwg.se_cust_man.cli

import de.htwg.se_cust_man.controllers.{CustomerController, SessionController}
import de.htwg.se_cust_man.cli.Cli
import jdk.javadoc.internal.doclets.toolkit.util.DocFinder.Input
import de.htwg.se_cust_man.server.ClientHelper
import scala.io.StdIn

@main
def runCli(): Unit = {
  Cli.startSocket()
  val mw: MainWindow = new MainWindow
  mw.loop()
  Cli.stopSocket()
}