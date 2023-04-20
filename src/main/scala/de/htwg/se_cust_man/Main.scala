package de.htwg.se_cust_man

import de.htwg.se_cust_man.models.DataModel

import scala.io.StdIn

case class Directory(name: String, parent: Directory, children: List[Directory])

enum Command {
  case ls, cd, rm, mk // filesystem commands
  case pwd, exit, help // shell commands
  case unknown // default, when commands is not known
}

object TuiController {
  def askCredentials(): Boolean = {
    val username = StdIn.readLine("username>")
    val password = StdIn.readLine("password>")

    username == "admin" && password == "admin"
  }

  def parse(input: String) : Command = {
    val v = input.split(" ")
    if(v.isEmpty) Command.unknown
    else v(0) match {
      case "ls" => Command.ls
      case "cd" => Command.cd
      case "rm" => Command.rm
      case "pwd" => Command.pwd
      case "exit" => Command.exit
      case "help" => Command.help
      case "mk" => Command.mk
      case _ => Command.unknown
    }
  }


  def prompt(directory: Directory): Directory = {

  }

  def renderer(): Int = {
    while (!askCredentials()) {
      println("Wrong credentials, try again!")
    }

    0
  }
}

def createDirTree(): Directory = {
  val customer = Directory("customer", null, List())
  val children = List(customer)
  Directory("admin", null, children)
}

@main
def start(): Unit = {
  TuiController.renderer()
  // main method body
}