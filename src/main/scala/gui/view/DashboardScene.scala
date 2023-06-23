package de.htwg.scm
package gui.view


import com.google.inject.{Guice, Injector}
import de.htwg.scm.models.Customer
import de.htwg.scm.store.IStore
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.beans.property.{IntegerProperty, ReadOnlyObjectProperty, ReadOnlyObjectWrapper, StringProperty}
import scalafx.collections.ObservableBuffer
import scalafx.scene.Scene
import scalafx.scene.control.{Button, ContentDisplay, ContextMenu, Label, MenuItem, TableCell, TableColumn, TableView}
import scalafx.scene.layout.VBox
import scalafx.stage.{Modality, Stage}
import net.codingwell.scalaguice.InjectorExtensions.*

class DashboardScene extends Scene {
  val injector: Injector = Guice.createInjector(new ScmModule)
  val controller: IStore[Customer] = injector.instance[IStore[Customer]]

  // TableView setup
  val customerProperties = ObservableBuffer[Customer]()
  customerProperties ++= controller.getAll

  val table = new TableView[Customer](customerProperties) {
    columns ++= List(
      new TableColumn[Customer, Int]("ID") {
        cellValueFactory = { cellData =>
          new ReadOnlyObjectWrapper[Int](this, "id", cellData.value.id)
        }
      },
      new TableColumn[Customer, String]("Name") {
        cellValueFactory = { cellData =>
          new StringProperty(this, "name", cellData.value.name)
        }
      },
      new TableColumn[Customer, String]("Email") {
        cellValueFactory = { cellData =>
          new StringProperty(this, "email", cellData.value.email)
        }
      },
      new TableColumn[Customer, String]("Phone Number") {
        cellValueFactory = { cellData =>
          new StringProperty(this, "phone", cellData.value.phone)
        }
      },
      new TableColumn[Customer, String]("Address") {
        cellValueFactory = { cellData =>
          new StringProperty(this, "address", cellData.value.address)
        }
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
        openCustomerStage(selectedCustomer)
      }
    }

    editMenuItem.setOnAction { _ =>
      val selectedCustomer = selectionModel().getSelectedItem
      if (selectedCustomer != null) {
        openEditorCustomerStage(selectedCustomer)
      }
    }

    deleteMenuItem.setOnAction { _ =>
      val selectedCustomer = selectionModel().getSelectedItem
      if (selectedCustomer != null) {
        if (controller.delete(selectedCustomer.id) > 0)
          customerProperties.remove(selectedCustomer)
      }
    }

    newMenuItem.setOnAction { _ =>
      openCustomerStage(Customer.empty)
    }
  }

  root = new VBox {
    children = List(
      new Label("Customers"),
      table
    )
  }

  private def openEditorCustomerStage(customer: Customer): Unit = {
    var customerEditorStage : Stage = null
    customerEditorStage= new Stage {
      title = if (customer.id < 0) "Create Customer" else "Edit Customer - " + customer.name
      scene = new CustomerEditorScene(controller, customer, () => customerEditorStage.close())
      initModality(Modality.ApplicationModal)
    }
    customerEditorStage.showAndWait()
  }

  private def openCustomerStage(customer: Customer): Unit = {
    var customerStage: Stage = null
    customerStage = new Stage {
      title = "Customer Details - " + customer.name
      scene = new CustomerViewScene(customer)
      initModality(Modality.ApplicationModal)
    }
    customerStage.showAndWait()
  }
}
