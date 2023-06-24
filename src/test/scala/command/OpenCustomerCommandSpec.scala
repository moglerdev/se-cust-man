package de.htwg.scm
package command

import command.OpenCustomerCommand
import org.scalatestplus.mockito.MockitoSugar
import org.mockito.Mockito.*
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import model.Customer
import state.CustomerState

class OpenCustomerCommandSpec extends AnyWordSpec with Matchers with MockitoSugar {

  "An OpenCustomerCommand" when {
    val initialState: Option[Customer] = None
    val newState: Option[Customer] = Some(Customer(1, "John Doe", "john@example.com", "1234567890", "Address"))

    val mockState: CustomerState = mock[CustomerState]
    when(mockState.option).thenReturn(initialState)

    val command: OpenCustomerCommand = OpenCustomerCommand(mockState, newState.get)

    "executed" should {
      "set the state to the new customer" in {
        command.execute() shouldBe true
        verify(mockState).set(newState)
      }
    }

    "undone" should {
      "restore the state to the previous customer" in {
        command.undo() shouldBe true
        verify(mockState).set(initialState)
      }
    }

    "redone" should {
      "set the state back to the new customer" in {
        command.redo() shouldBe true
        verify(mockState, times(2)).set(newState)
      }
    }
  }
}
