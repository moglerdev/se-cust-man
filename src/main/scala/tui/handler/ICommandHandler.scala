package de.htwg.scm
package tui.handler

import command.ICommand

import scala.util.{Failure, Try}

trait ICommandHandler {
  def setNext(handler: ICommandHandler): ICommandHandler
  def handle(command: String): Try[ICommand]
}

abstract class CommandHandler() extends ICommandHandler {
  private var nextHandler: Option[ICommandHandler] = None

  override def setNext(handler: ICommandHandler): ICommandHandler = {
    nextHandler = Some(handler)
    handler
  }

  override def handle(command: String): Try[ICommand] = {
    if (nextHandler.isDefined) {
      nextHandler.get.handle(command)
    } else
      Failure(new Exception("No handler found for command: " + command))
  }
}
