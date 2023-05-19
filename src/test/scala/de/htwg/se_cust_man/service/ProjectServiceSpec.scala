package de.htwg.se_cust_man.service

import org.scalatest.*
import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec
import de.htwg.se_cust_man.builder.{ProjectBuilder, TaskBuilder}
import java.util.Date
import de.htwg.se_cust_man.service.ProjectService
import java.security.Timestamp
import de.htwg.se_cust_man.Project

class ProjectServiceSpec extends AnyWordSpec with Matchers {
  "Project Service Test" should {
    val test = ProjectService.getInstance("test");
    "create a Project" in {
      val project = test.insertProject(Project(1, "okay", "okay", new Date(0), 1), Vector())
      project.title must be("okay")
      project.description must be("okay")
      project.deadline must be(new Date(0))
      project.customerId must be(1)

    }
    "get a Project by id" in {
      val customer = test.getProjectById(1)
      customer.get.title must be("Test Project 1")
      customer.get.description must be("Test Project 1")
      customer.get.deadline must be(new Date(0))
      customer.get.customerId must be(1)


    }
    "get all Projects" in {
      val customers = test.getProjects
      customers.size must be(1)
      customers(0).title must be("Test Project 1")
      customers(0).description must be("Test Project 1")
      customers(0).deadline must be(new Date(0))
      customers(0).customerId must be(1)

  
    }
  }
}
