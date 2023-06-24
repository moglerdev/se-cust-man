package de.htwg.scm
package command

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import org.mockito.Mockito._

class QuitCommandSpec extends AnyWordSpec with Matchers {
  "A QuitCommand" when {
    val command: QuitCommand = new QuitCommand()

    "undone" should {
      "return false as the command does not support undo" in {
        command.undo() should be(false)
      }
    }

    "redone" should {
      "return false as the command does not support redo" in {
        command.redo() should be(false)
      }
    }
  }
}
