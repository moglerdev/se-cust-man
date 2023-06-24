package de.htwg.scm
package gui.view

import com.google.inject.Guice
import de.htwg.scm.model.{Customer, Project}
import de.htwg.scm.store.IStore
import scalafx.beans.property.{ReadOnlyObjectWrapper, StringProperty}
import scalafx.collections.ObservableBuffer
import scalafx.scene.control.{ContextMenu, MenuItem, TableColumn, TableView}
import scalafx.scene.layout.{HBox, VBox}
import scalafx.scene.text.Text
import scalafx.scene.{Node, Scene}
import net.codingwell.scalaguice.InjectorExtensions.*
import scalafx.scene.control.cell.TextFieldTableCell
import scalafx.stage.{Modality, Stage}

class CustomerViewScene(customer: Customer) extends Scene {

  val injector = Guice.createInjector(new ScmModule)
  val controller: IStore[Project] = injector.instance[IStore[Project]]

  val customerInfo: Node = new VBox {
    children = Seq(
      new Text(s"ID: ${customer.id}"),
      new Text(s"Name: ${customer.name}"),
      new Text(s"Email: ${customer.email}"),
      new Text(s"Phone: ${customer.phone}"),
      new Text(s"Address: ${customer.address}")
    )
  }

  val oberservableProjects = ObservableBuffer[Project]()
  oberservableProjects ++= controller.getAll.filter(_.customer_id == customer.id)

  val projectTable: Node = new TableView[Project](oberservableProjects) {
    columns ++= List(
      new TableColumn[Project, Int]("ID") {
        cellValueFactory = { cellData =>
          new ReadOnlyObjectWrapper[Int](this, "id", cellData.value.id)
        }
      },
      new TableColumn[Project, String] {
        text = "Title"
        cellValueFactory = { cellData =>
          new StringProperty(this, "name", cellData.value.title)
        }
        cellFactory = TextFieldTableCell.forTableColumn[Project]()
        editable = true
        prefWidth = 200
      },
      new TableColumn[Project, String] {
        text = "Description"
        cellValueFactory = { cellData =>
          new StringProperty(this, "email", cellData.value.description)
        }
        cellFactory = TextFieldTableCell.forTableColumn[Project]()
        editable = true
        prefWidth = 300
      }
    )

    // Context menu setup
    val openMenuItem = new MenuItem("Open")
    val editMenuItem = new MenuItem("Edit")
    val deleteMenuItem = new MenuItem("Delete")
    val newMenuItem = new MenuItem("New")

    contextMenu = new ContextMenu(openMenuItem, editMenuItem, deleteMenuItem, newMenuItem)

    openMenuItem.setOnAction { _ =>
      val selectedCustomer = selectionModel().getSelectedItem
      if (selectedCustomer != null) {
//        openProject(selectedCustomer)
      }
    }

    deleteMenuItem.setOnAction { _ =>
      val selectedCustomer = selectionModel().getSelectedItem
      if (selectedCustomer != null) {
        if (controller.delete(selectedCustomer.id) > 0)
          oberservableProjects.remove(selectedCustomer)
      }
    }

    newMenuItem.setOnAction { _ =>
      oberservableProjects += Project.empty(customer)
    }
  }

  root = new HBox {
    spacing = 20
    children = Seq(customerInfo, projectTable)
  }

//
//  root.prefWidth <== width
//  root.prefHeight <== height
}
