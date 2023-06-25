package de.htwg.scm
package view.tui.gui

import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.control.Button
import scalafx.scene.layout.VBox

class GuiApp extends JFXApp3 {
  override def start(): Unit = {

    val signInScene = new SignInScene()

    val dashboardScene = new DashboardScene()

    // Set the scene on the stage
    stage = new PrimaryStage() {
      title = "Hello World!"
      scene = dashboardScene
    }

  }

}

