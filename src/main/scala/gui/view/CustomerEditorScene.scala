package de.htwg.scm
package gui.view

import de.htwg.scm.model.Customer
import de.htwg.scm.store.IStore
import scalafx.Includes.*
import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.collections.ObservableBuffer
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.control.{Button, TextField}
import scalafx.scene.layout.{HBox, VBox}

import scala.reflect.runtime.universe.*


class CustomerEditorScene(
                           controller: IStore[Customer],
                           customer: Customer,
                           onClose: () => Unit,
) extends Scene {

  val nameField = new TextField()
  nameField.text = customer.name
  val emailField = new TextField()
  emailField.text = customer.email
  val phoneNumberField = new TextField()
  phoneNumberField.text = customer.phone
  val addressField = new TextField()
  addressField.text = customer.address

  val saveButton = new Button("Save")
  saveButton.onAction = _ => {
    if (customer.id < 0) controller.create(getCustomer)
    else controller.update(customer.id, getCustomer)
    onClose()
  }
  val cancelButton = new Button("Cancel")
  cancelButton.onAction = _ => onClose()


  val editorLayout: VBox = new VBox {
    spacing = 10
    padding = Insets(10)
    children = Seq(
      new HBox {
        spacing = 10
        children = Seq(new scalafx.scene.control.Label("Name:"), nameField)
      },
      new HBox {
        spacing = 10
        children = Seq(new scalafx.scene.control.Label("Email:"), emailField)
      },
      new HBox {
        spacing = 10
        children = Seq(new scalafx.scene.control.Label("Phone Number:"), phoneNumberField)
      },
      new HBox {
        spacing = 10
        children = Seq(new scalafx.scene.control.Label("Address:"), addressField)
      },
      new HBox {
        spacing = 10
        children = Seq(saveButton, cancelButton)
      }
    )
  }

  root = editorLayout

  def getCustomer: Customer = {
    val name = nameField.text.value
    val email = emailField.text.value
    val phoneNumber = phoneNumberField.text.value
    val address = addressField.text.value

    Customer(customer.id, name, email, phoneNumber, address)
  }
}
