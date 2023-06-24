package de.htwg.scm;
package tui.view

import com.google.inject.{Guice, Injector}

import scala.io.StdIn.readLine
import scala.util.matching.Regex
import scala.util.{Failure, Try}


trait ITuiView extends IDispose {
    def setNext(next: ITuiView): ITuiView
    def handle(command: String): Try[String]
    def dispose(): Unit
}

abstract class TuiModelView(val prefix: String) extends ITuiView {
    val pattern: Regex = """^(\w+)\s+(\w+)(?:\s+(.*))?$""".r

    private var next: Option[ITuiView] = None
    def setNext(next: ITuiView): ITuiView = {
        this.next = Some(next)
        next
    }

    def handleCommand(command: String, args: String): Try[String]

    def handle(command: String): Try[String] = {
        command match {
            case "exit" => Try("exit")
            case pattern(_, cmd, args) => handleCommand(cmd, args)
            case _ => next match {
                case Some(n) => n.handle(command)
                case None => Failure(new Exception("No matching command found"))
            }
        }
    }
}
