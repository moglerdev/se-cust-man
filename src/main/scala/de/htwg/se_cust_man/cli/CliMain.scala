package de.htwg.se_cust_man.cli

import de.htwg.se_cust_man.cli.view.CliMainView
import de.htwg.se_cust_man.controllers.UserController


@main
def runCli(): Unit = {
  val mainView = new CliMainView
  mainView.render()
  mainView.close()
}