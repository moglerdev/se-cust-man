package de.htwg.scm
package gui.view

import de.htwg.scm.models.Project
import de.htwg.scm.store.IStore
import scalafx.Includes._
import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.collections.ObservableBuffer
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.control.{Button, TextField}
import scalafx.scene.layout.{HBox, VBox}

import scala.reflect.runtime.universe.*

class ProjectEditorScene(
                          controller: IStore[Project],
                          project: Project,
                          onClose: () => Unit,
                        ) extends Scene {

  val titleField = new TextField()
  titleField.text = project.title
  val descriptionField = new TextField()
  descriptionField.text = project.description

  val saveButton = new Button("Save")
  saveButton.onAction = _ => {
    if (project.id < 0) controller.create(getProject)
    else controller.update(project.id, getProject)
    onClose()
  }
  val cancelButton = new Button("Cancel")
  cancelButton.onAction = _ => onClose()

  val editorLayout = new VBox {
    spacing = 10
    padding = Insets(10)
    children = Seq(
      new HBox {
        spacing = 10
        children = Seq(new scalafx.scene.control.Label("Title:"), titleField)
      },
      new HBox {
        spacing = 10
        children = Seq(new scalafx.scene.control.Label("Description:"), descriptionField)
      },
      new HBox {
        spacing = 10
        children = Seq(saveButton, cancelButton)
      }
    )
  }

  root = editorLayout

  def getProject: Project = {
    val title = titleField.text.value
    val description = descriptionField.text.value

    Project(project.id, project.customer_id, title, description)
  }
}
