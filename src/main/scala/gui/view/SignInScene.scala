package de.htwg.scm
package gui.view

import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.layout.VBox

class SignInScene extends Scene {
  val signInView = new SignInView

  root = new VBox {
    children = Seq(signInView.view)
  }

  signInView.onSignIn { (username, password) =>
    if (isValidUser(username, password)) {
      signInView.setErrorMessage("")
      println("Sign in successful")
      // Here you can implement your further logic after successful sign-in
    } else {
      signInView.setErrorMessage("Invalid username or password")
    }
  }

  def isValidUser(username: String, password: String): Boolean = {
    // Check username and password against your authentication logic/database here
    // Return true if the user is valid, otherwise return false
    // This is a placeholder implementation, you need to replace it with your own logic
    username == "admin" && password == "password"
  }
}
