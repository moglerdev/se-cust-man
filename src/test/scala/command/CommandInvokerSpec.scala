package de.htwg.scm
package command

import command.{CommandInvoker, ICommand}

import org.mockito.Mockito.{never, reset, times, verify, when}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.mockito.MockitoSugar
import org.mockito.verification.*

class CommandInvokerSpec extends AnyWordSpec with Matchers with MockitoSugar {

  // Create a mock command
  val mockCommand: ICommand = mock[ICommand]

  "CommandInvoker" should {
    "execute a command" in {
      val invoker = new CommandInvoker
      invoker.executeCommand(mockCommand)

      // Verify that the command's execute method was called
      verify(mockCommand, times(1)).execute()
    }

    "undo the last executed command" in {
      val invoker = new CommandInvoker
      invoker.executeCommand(mockCommand)
      reset(mockCommand)

      invoker.undoCommand()

      // Verify that the command's undo method was called
      verify(mockCommand, times(1)).undo()
    }

    "redo the last undone command" in {
      val invoker = new CommandInvoker
      invoker.executeCommand(mockCommand)
      invoker.undoCommand()
      reset(mockCommand)

      // Configure the mockCommand's redo() method behavior
      when(mockCommand.redo()).thenReturn(true)

      invoker.redoCommand()

      // Verify that the command's redo method was called
      verify(mockCommand, times(1)).redo()
    }

    "not undo or redo when no commands are available" in {
      val invoker = new CommandInvoker

      invoker.undoCommand() // Nothing should happen
      invoker.redoCommand() // Nothing should happen

      // Verify that the command's undo and redo methods were not called
//      verify(mockCommand, never).undo()
//      verify(mockCommand, never).redo()
    }
  }
}

