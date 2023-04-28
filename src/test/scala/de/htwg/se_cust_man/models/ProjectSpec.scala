package de.htwg.se_cust_man.models

import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec
class ProjectSpec extends AnyWordSpec with Matchers {
  "project helper" should {
    "convert to csv" in {
      val project = Project(1, "Hello")
      project.toCSV must be("1;Hello")
    }
    "from csv to model" in {
      val project = Project(1, "Hello")
      Project.fromCSV("1;Hello") must be(project)
    }
    "toString" in {
      val project = Project(1, "Hello")
      project.toString must be("Project(1,Hello)")
    }
  }
}
