package de.htwg.se_cust_man.cli

import de.htwg.se_cust_man.models.{Customer, Project}
import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec

class MainWindowSpec extends AnyWordSpec with Matchers {
  "Test MainWidnow" should {
    val mainWindow = new MainWindow()
    mainWindow.customersL = Vector(Customer.empty)
    mainWindow.projectsL = Vector(Project.empty)
    "generateList" in {
      mainWindow.generateList() must be (mainWindow.generateList())
    }

    "open customer" in {
      mainWindow.openCustomer(0) must be (mainWindow.openCustomer(0))
      mainWindow.openCustomer(-1) must be (mainWindow.openCustomer(-1))
    }

    "open project" in {
      mainWindow.openProject(0) must be (mainWindow.openProject(0))
      mainWindow.openProject(-1) must be (mainWindow.openProject(-1))
    }

    "open" in {
    }
  }

}
