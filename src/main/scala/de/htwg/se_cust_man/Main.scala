package de.htwg.se_cust_man

import de.htwg.se_cust_man.models.DataModel

import scala.::
import scala.annotation.tailrec
import scala.io.StdIn

case class Directory(name: String, parent: Option[Directory], getChildren: Directory => List[Directory])

enum Command {
  case ls, cd, rm, mk // filesystem commands
  case pwd, exit, help // shell commands
  case unknown // default, when commands is not known
}

object TuiController:

  def checkCredentials(username: String, password: String): Boolean = {
    username == "admin" && password == "admin"
  }

  def askCredentials(): (String, String) = {
    val username = StdIn.readLine("username>")
    val password = StdIn.readLine("password>")

    (username, password)
  }

  def parse(input: String) : (Command, List[String]) = {
    val v = input.split(" ")
    val params = v.toList.slice(1, v.length)
    if(v.isEmpty) (Command.unknown, params)
    else (v(0) match {
      case "ls" => Command.ls
      case "cd" => Command.cd
      case "rm" => Command.rm
      case "pwd" => Command.pwd
      case "exit" => Command.exit
      case "help" => Command.help
      case "mk" => Command.mk
      case _ => Command.unknown
    }, params)
  }

  def promptPrefix(directory: Directory): String = {
    var name = directory.name
    var dir = directory
    while (dir.parent.isDefined) {
      dir = dir.parent.get
      name = dir.name + "/" + name
    }
    name
  }

  def prompt(directory: Directory): Directory = {
    val prefix = promptPrefix(directory) + ">"
    val in = StdIn.readLine(prefix)
    val cmd = parse(in)


    directory
  }

  def renderer(): Int = {
    var creds : (String, String) = ("","");
    while((creds = askCredentials(), checkCredentials(creds._1, creds._2))(1)) {
      creds = askCredentials()
      println("Wrong credentials, try again!")
    }

    var dir = createDirTree("admin")
    while (true) {
      TuiController.prompt()
    }

    0
  }
end TuiController

def generateCustomerDir(parent: Directory) : List[Directory] = {
  List(
    Directory("Gauß", Option(parent), generateCustomerDir),
    Directory("Euler", Option(parent), generateCustomerDir),
    Directory("Fermat", Option(parent), generateCustomerDir),
    Directory("Fibonacci", Option(parent), generateCustomerDir),
    Directory("Newton", Option(parent), generateCustomerDir),
    Directory("Leibniz", Option(parent), generateCustomerDir),
    Directory("Lagrange", Option(parent), generateCustomerDir),
  )
}

def createDirTree(username: String): Directory = {
  Directory(username, None, generateCustomerDir)
}

@main
def start(): Unit = {
  TuiController.renderer()
  // main method body
}