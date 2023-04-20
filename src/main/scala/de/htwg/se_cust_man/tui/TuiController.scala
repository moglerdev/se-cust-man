package de.htwg.se_cust_man.tui

import scala.io.StdIn
import de.htwg.se_cust_man.tui._

import de.htwg.se_cust_man.models.User

class TuiController(user: User) {
  private val ROOT = Directory(user.username, None, List(
    Directory("home", None, List()),
    Directory("etc", None, List()),
    Directory("bin", None, List()),
    Directory("usr", None, List(
      Directory("local", None, List()),
      Directory("share", None, List()),
      Directory("bin", None, List())
    ))
  ))

  private def promptPrefix(dir: Directory) : String = {
    dir.getPath + ">"
  }

  def execute(dir: Directory, cmd: String, params: Vector[String]): Option[Executed] = {
    val input = Input(cmd, params)
    input.cmd match {
      case "exit" => System.exit(0); None
      case "cd" => {
        if (input.args.isEmpty || !input.args(0).trim().startsWith("/")) None
        else Some(Directory.changeDir(input, ROOT))
      }
      case _ => None
    }
  }

  private def readPrompt(dir: Directory): Option[Executed] = {
    val prompt = promptPrefix(dir);
    val input = StdIn.readLine(prompt).split(" ")

    val cmd = input(0).trim()
    val params = input.toVector.slice(1, input.length)

    this.execute(dir, cmd, params) match
      case Some(e) => Some(e)
      case None => dir.execute(Input(cmd, params))
  }

  def start(): String = {
    renderer(ROOT)
  }

  @scala.annotation.tailrec
  private final def renderer(dir: Directory) : String = {
    readPrompt(dir) match {
      case Some(e) =>
        if (e.msg.isDefined) println(e.msg.get)
        renderer(e.dir.getOrElse(dir))
      case None => println("ERROR: Unknown Command."); renderer (dir)
    }
  }
}
