package de.htwg.se_cust_man.cli.view

import de.htwg.se_cust_man.Observer
import de.htwg.se_cust_man.cli.Input

trait CliView extends Observer{
  def promptPrefix: String
  def isOpen: Boolean
  def eval(input: Input) : Option[String]
  def close(): Unit
}
