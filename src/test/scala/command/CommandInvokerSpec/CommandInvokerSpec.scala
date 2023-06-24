package de.htwg.scm
package command.CommandInvokerSpec

import de.htwg.scm.command.{CommandInvoker, ICommand}
import org.scalatest.*
import org.scalatest.flatspec.FlatSpec
import org.scalatest.matchers.must.Matchers
import org.scalatest.matchers.should.Matchers.shouldEqual

class CommandInvokerSpec extends FlatSpec with Matchers {
  "A CommandInvoker" should "execute a command and store it in the history" in {
    val invoker = new CommandInvoker()
    val command = new MockCommand()

    invoker.executeCommand(command)

    invoker.undoCommand() // Undo the executed command

    invoker.redoCommand() // Redo the command

    command.executeCount.shouldEqual(1) // The command should have been executed twice
    command.undoCount.shouldEqual(1) // The command should have been undone once
    command.redoCount shouldEqual 1 // The command should have been redone once
  }

  it should "undo the last executed command" in {
    val invoker = new CommandInvoker()
    val command1 = new MockCommand()
    val command2 = new MockCommand()

    invoker.executeCommand(command1)
    invoker.executeCommand(command2)

    invoker.undoCommand()

    command2.undoCount shouldEqual 1 // The second command should have been undone
    command1.undoCount shouldEqual 0 // The first command should not have been undone
  }

  it should "redo the last undone command" in {
    val invoker = new CommandInvoker()
    val command1 = new MockCommand()
    val command2 = new MockCommand()

    invoker.executeCommand(command1)
    invoker.executeCommand(command2)

    invoker.undoCommand()
    invoker.redoCommand()

    command2.redoCount shouldEqual 1 // The second command should have been redone
    command1.redoCount shouldEqual 0 // The first command should not have been redone
  }

  it should "print 'No commands to undo.' when there are no commands to undo" in {
    val invoker = new CommandInvoker()
    val outContent = new java.io.ByteArrayOutputStream()
    Console.withOut(outContent) {
      invoker.undoCommand()
    }
    val output = outContent.toString.stripLineEnd

    output shouldEqual "No commands to undo."
  }

  it should "print 'No commands to redo.' when there are no commands to redo" in {
    val invoker = new CommandInvoker()
    val outContent = new java.io.ByteArrayOutputStream()
    Console.withOut(outContent) {
      invoker.redoCommand()
    }
    val output = outContent.toString.stripLineEnd

    output shouldEqual "No commands to redo."
  }
}

// A mock command for testing purposes
class MockCommand extends ICommand {
  var executeCount = 0
  var undoCount = 0
  var redoCount = 0

  override def execute(): Boolean = {
    executeCount += 1
    true
  }

  override def undo(): Boolean = {
    undoCount += 1
    true
  }

  override def redo(): Boolean = {
    redoCount += 1
    true
  }
}
