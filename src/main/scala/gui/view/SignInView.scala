package de.htwg.scm
package gui.view

import scalafx.scene.control.{Button, PasswordField, TextField}
import scalafx.scene.layout.{HBox, VBox}
import scalafx.scene.text.Text
import scalafx.geometry.Insets

class SignInView {
  val usernameLabel = new Text("Username:")
  val usernameField = new TextField()

  val passwordLabel = new Text("Password:")
  val passwordField = new PasswordField()

  val signInButton = new Button("Sign In")

  val errorMessage = new Text()

  val view: VBox = new VBox() {
    spacing = 10
    padding = Insets(10)
    style = "-fx-background-color: #f2f2f2;"
    children = Seq(
      new Text {
        text = "Sign In"
        style = "-fx-font-size: 18px; -fx-font-weight: bold;"
      },
      new VBox() {
        spacing = 5
        children = Seq(
          new HBox() {
            spacing = 5
            children = Seq(usernameLabel, usernameField)
          },
          new HBox() {
            spacing = 5
            children = Seq(passwordLabel, passwordField)
          }
        )
      },
      signInButton,
      errorMessage
    )
  }

  def setUsername(username: String): Unit = usernameField.text = username

  def setPassword(password: String): Unit = passwordField.text = password

  def setErrorMessage(message: String): Unit = errorMessage.text = message

  def onSignIn(handler: (String, String) => Unit): Unit = {
    signInButton.onAction = _ => {
      val username = usernameField.text.value
      val password = passwordField.text.value
      handler(username, password)
    }
  }
}
