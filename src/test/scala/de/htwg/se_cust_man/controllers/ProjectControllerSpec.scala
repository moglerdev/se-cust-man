package de.htwg.se_cust_man.controllers

import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec
import de.htwg.se_cust_man.models.Project
class ProjectControllerSpec extends AnyWordSpec with Matchers{
  "A ProjectController" should {
    val default : Vector[Project] = Vector(Project(0, "Hey"))
    val controller = new ProjectController(default)
    "have a Project" in {
      controller.getProjects must be(default)
    }
    "have a Project by id" in {
      controller.getProjectById(default.head.id).get.title must be(default.head.title)
    }
    "have a Project by name" in {
      controller.getProjectByTitle(default.last.title).get must be(default.last)
    }
    "remove a Project" in {
      controller.removeProject(default.head) must be(true)
    }
    "add a Project" in {
      controller.addProject(default.head) must be(true)
    }
  }
}
