package de.htwg.se_cust_man.cli.view

import de.htwg.se_cust_man.controllers.{CustomerController, ProjectController}
import de.htwg.se_cust_man.models.Project

class CliListMain(customerCltr: CustomerController, projectCltr: ProjectController) extends CliView {
  override def onNotify(): Unit = {}
  override def render(): Unit = {
    toPrint.foreach(println)
  }
  override def close(): Unit = {}
}
