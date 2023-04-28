package de.htwg.se_cust_man.cli

import de.htwg.se_cust_man.server.ClientHelper

import scala.io.StdIn

case class Input(command: String, arguments: Vector[String])

object Cli {
  def prompt(prefix: String) : Input = {
    val c = StdIn.readLine(prefix + "> ").trim.split(" ")
    Input(c.head, c.tail.toVector)
  }
  def prompt(prefix: Vector[String]): Input = {
    prompt(prefix.mkString("/"))
  }

  var client : Option[ClientHelper] = None

  def startSocket(): Unit = {
    client = Some(new ClientHelper)
    client.get.startConnection("127.0.0.1", 25565)
  }

  def send(msg: String): Unit = {
    if(client.isDefined) client.get.sendMessage(msg)
  }

  def subscribe(onMessage: (String) => Unit): Unit = {
    if (client.isDefined) client.get.subscribe(onMessage)
  }

  def stopSocket(): Unit = {
    if (client.isDefined)
      client.get.stopConnection()
  }
}
