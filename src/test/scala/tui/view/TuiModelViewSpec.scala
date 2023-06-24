package de.htwg.scm
package tui.view

import scala.util.{Failure, Success, Try}
import tui.view.{ITuiView, TuiModelView}

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.language.postfixOps

class TuiModelViewSpec extends AnyWordSpec with Matchers {

  // Create a test implementation of ITuiView
  class TestTuiView(prefix: String) extends TuiModelView(prefix) {
    override def handleCommand(command: String, args: String): Try[String] = {
      command match {
        case "sayHello" => Success(s"Hello, $args!")
        case _ => Success("Unknown command")
      }
    }

    override def dispose(): Unit = {}
  }

  "TuiModelView" should {

    "handle valid command" in {
      // Create an instance of the TestTuiView
      val view = new TestTuiView("Prefix")

      // Set the next view
      val nextView = new TestTuiView("NextPrefix")
      view.setNext(nextView)

      // Call the handle method with a valid command
      val result = view.handle("Prefix sayHello John")

      // Verify the result
      result should be(Success("Hello, John!"))
    }

    "handle exit command" in {
      val view = new TestTuiView("Prefix")

      val result = view.handle("exit")

      result should be(Success("exit"))
    }

    "handle unknown command" in {
      val view = new TestTuiView("Prefix")

      val result = view.handle("UnknownCommand")

      result shouldBe a[Failure[_]]
    }

    "handle command with no matching view" in {
      val view = new TestTuiView("Prefix")

      val result = view.handle("SomeCommand")

      result shouldBe a[Failure[_]]
    }
  }
}
