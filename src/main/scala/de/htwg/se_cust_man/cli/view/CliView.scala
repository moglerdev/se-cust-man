package de.htwg.se_cust_man.cli.view

import de.htwg.se_cust_man.Observer

import scala.io.StdIn

abstract class CliView extends Observer{

  final protected def write(msg: String): Unit = println(msg)
  final protected def prompt(prefix: String): String = {
    val t = StdIn.readLine(prefix + "> ")
    if (t == null) {
      print("Bye")
      System.exit(0)
    }
    t
  }
  def render(): Unit
  def close(): Unit
}
