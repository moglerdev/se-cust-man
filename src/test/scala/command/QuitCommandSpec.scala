import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

package de.htwg.scm.command

class QuitCommandSpec extends AnyFlatSpec with Matchers {

  "A QuitCommand" should "execute successfully and exit the program" in {
    val quitCommand = new QuitCommand()

    // Execute the command
    val executeResult = quitCommand.execute()

    // Verify that the command executed successfully
    executeResult should be(true)

    // TODO: Add additional assertions to test any necessary cleanup or finalization tasks
  }

  it should "not support undo operation" in {
    val quitCommand = new QuitCommand()

    // Attempt to undo the command
    val undoResult = quitCommand.undo()

    // Verify that undo is not supported
    undoResult should be(false)
  }

  it should "not support redo operation" in {
    val quitCommand = new QuitCommand()

    // Attempt to redo the command
    val redoResult = quitCommand.redo()

    // Verify that redo is not supported
    redoResult should be(false)
  }

}
