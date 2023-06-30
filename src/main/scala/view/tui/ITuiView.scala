package de.htwg.scm
package view.tui

import com.google.inject.{Guice, Injector}
import de.htwg.scm.controller.IModelController

import scala.io.StdIn.readLine
import scala.util.matching.Regex
import scala.util.{Failure, Success, Try}


trait ITuiView extends IDispose {
    def setNext(next: ITuiView): ITuiView
    def clearNext(): ITuiView
    def handle(command: String): Try[String]
    def dispose(): Unit
}

abstract class TuiModelView[TModel] (
    val prefix: String,
    val controller: IModelController[TModel])
    extends ITuiView with IObserver {
    controller.observe(this)

    private var next: Option[ITuiView] = None
    def getNext: Option[ITuiView] = next
    def setNext(next: ITuiView): ITuiView = {
        if (this.next.isDefined) this.next.get.dispose()
        this.next = Some(next)
        next
    }
    def clearNext(): ITuiView = {
        if (next.isDefined) next.get.dispose()
        next = None
        this
    }

    protected def convertToModel(args: String): Try[TModel]
    protected def handleSet(model: TModel): Try[String]
    protected def handleList(): Try[String]
    protected def handleHelp(): Try[String]

    protected def handleCommand(cmd: String, args: String): Try[String] = Failure(new IllegalArgumentException("No command given"))

    private def remove(args: String): Try[String] = {
        val regex: Regex = """(\d+)""".r
        val ids = regex.findAllIn(args).map(_.toInt).toList

        if (ids.isEmpty) {
            Failure(new IllegalArgumentException("No IDs found"))
        } else {
            val removalResults = ids.flatMap { taskId =>
                val task = controller.get(taskId)
                if (task.isEmpty) {
                    Some(Failure(new IllegalArgumentException(s"Item with ID $taskId not found")))
                } else {
                    controller.remove(task.get)
                    Some(Success(s"Item $taskId removed"))
                }
            }

            if (removalResults.exists(_.isFailure)) {
                val errorMessages = removalResults.collect { case Failure(exception) => exception.getMessage }
                Failure(new IllegalArgumentException(errorMessages.mkString(", ")))
            } else {
                Success(s"Items ${ids.mkString(", ")} removed")
            }
        }
    }

    private def set(args: String): Try[String] ={
        if (args.isEmpty) {
            return Failure(new IllegalArgumentException("Wrong arguments for add command"))
        }
        val task = convertToModel(args)
        if (task.isFailure) {
            Failure(task.failed.get)
        } else handleSet(task.get)
    }

    val pattern: Regex = """^(\w+)\s+(\w+)(?:\s+(.*))?$""".r
    def handle(command: String): Try[String] = {
        command match {
            case "exit" => Success("exit")
            case pattern(pre, cmd, args) if pre == prefix =>
                cmd match {
                    case "set" => set(args)
                    case "rm" => remove(args)
                    case "ls" => handleList()
                    case "help" => handleHelp()
                    case _ => handleCommand(cmd, args)
                }
            case _ =>
                next match {
                    case Some(n) => n.handle(command)
                    case None => Failure(
                        new IllegalArgumentException(
                            "No matching command found"))
                }
        }
    }


    override def dispose(): Unit = {
        controller.unobserve(this)
        clearNext()
    }
}
