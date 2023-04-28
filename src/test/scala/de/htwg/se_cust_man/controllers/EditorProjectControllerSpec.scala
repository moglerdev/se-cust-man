package de.htwg.se_cust_man.controllers

import de.htwg.se_cust_man.models.Project
import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec

class EditorProjectControllerSpec extends AnyWordSpec with Matchers {
  "EditorProjectController" should {
    val default : Vector[Project] = Vector(Project(0, "Name"))
    "incoming message handling" in {
      val controller = new EditorProjectController(default)
      controller.openProject(0)
      controller.isNewProject must be(false)
      controller.getOpenProject.get must be(default.head)

      val project = Project(10, "Name")
      controller.onMessage(s"PROJECT::NEW::${project.toCSV}")
      controller.openProject(10)

      val updateProject = project.copy(title = "NewName")
      controller.onMessage(s"PROJECT::UPDATE::${updateProject.toCSV}")

      controller.onMessage(s"PROJECT::DELETE::${updateProject.toCSV}")

      controller.onMessage(s"asdasd")
    }

    "get open Project" in {
      val controller = new EditorProjectController(default)
      controller.isOpen must be(false)

      controller.openProject(0).get must be(default.head)
      controller.getOpenProject.get.title must be(default.head.title)
      controller.isOpen must be(true)
    }

    "update value" in {
      val controller = new EditorProjectController(default)
      val project = Project(10, "Name")
      controller.updateValues("title", "NewName") must be(false)

      controller.createProject(Some(project))
      controller.updateValues("title", "NewName") must be(true)
      controller.getOpenProject.get.title  must be("NewName")

      controller.updateValues("spock", "NewName") must be(false)
      controller.getOpenProject.get.title must be("NewName")

    }

    "save project" in {
      val controller = new EditorProjectController(default)
      val project = Project(10, "Name")
      controller.saveProject() must be(false)

      controller.createProject(Some(project))
      controller.updateValues("title", "NewName")
      controller.saveProject() must be(true)
      controller.saveProject(closeAfter = true) must be(true)

      controller.openProject(default.head.id)
      controller.isNewProject must be(false)
      controller.updateValues("title", "NewName")
      controller.saveProject() must be(true)
    }

    "create Project" in {
      val controller = new EditorProjectController(default)
      controller.isNewProject must be(false)
      val project = Project(10, "Name")
      controller.createProject(Some(project))
      controller.getOpenProject.get.title must be(project.title)
      controller.isNewProject must be(true)

      controller.createProject()
      controller.getOpenProject.get.title must be("")
      controller.isNewProject must be(true)
    }
  }
}
