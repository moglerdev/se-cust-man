package de.htwg.scm
package tui.handler

import controller.{EditorController, IEditorController, ISessionController}

import de.htwg.scm.models.{Customer, Project, Task}


case class Request(input: String)

trait IHandler {
    def setNext(h: IHandler) : IHandler
    def handle(request: Request) : String
}

abstract class BaseHandler extends IHandler {
  private var next: Option[IHandler] = None

  override def setNext(h: IHandler): IHandler = {
    next = Some(h)
    h
  }

  override def handle(request: Request): String = {
    if (next.isDefined) next.get.handle(request)
    else "Unknown Request"
    // TODO: DPI Error Message
  }
}

class CloseHandler() extends BaseHandler {
  override def handle(request: Request): String = {
    if (request.input.startsWith("exit")) {
      ".exit"
    } else super.handle(request)
  }
}

class AuthHandler(sessionController: ISessionController) extends BaseHandler {

  def signIn(request: Request): String = {
    val x = request.input.split(" ")
    if (x.length < 3) return "[WRONG] signin <username> <password>"
    if (sessionController.isSignedIn) return "[FAILED] You already signed in!"
    if (sessionController.signIn(x(1), x(2))) return "[SUCCESS] You are now signed in!"
    "[FAILED] Wrong credentials!"
  }

  override def handle(request: Request): String = {
    if (request.input.startsWith("signin")) {
      signIn(request)
    } else if (sessionController.isSignedIn) {
      super.handle(request)
    } else "[FAILED] You are not permitted for this!"
  }
}

class CustomerHandler(editorController: IEditorController[Customer]) extends BaseHandler {
  override def handle(request: Request): String = {
    if (request.input.startsWith("customer")) {
      // Logik zum Öffnen des Customers hier einfügen
      // TODO:
      "Mach was du."
    } else super.handle(request) // Weitergabe der Anfrage an den nächsten Handler
  }
}

class ProjectHandler(projectController: IEditorController[Project]) extends BaseHandler {
  override def handle(request: Request): String = {
    if (request.input.startsWith("project")) {
      // Logik zum Öffnen des Customers hier einfügen
      // TODO:
      "Mach was du."
    } else super.handle(request) // Weitergabe der Anfrage an den nächsten Handler
  }
}

class TaskHandler(taskController: IEditorController[Task]) extends BaseHandler {
  override def handle(request: Request): String = {
    if (request.input.startsWith("task")) {
      // Logik zum Öffnen des Customers hier einfügen
      // TODO:
      "Mach was du."
    } else super.handle(request) // Weitergabe der Anfrage an den nächsten Handler
  }
}