package de.htwg.scm
package model

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import model.{Customer, Project}

class ProjectSpec extends AnyFlatSpec with Matchers {

  "An empty Project" should "have default values" in {
    val customer = Customer(1, "John Doe", "john.doe@example.com", "1234567890", "123 Main St")
    val emptyProject = Project.empty(customer)

    emptyProject.id should be(-1)
    emptyProject.customer_id should be(customer.id)
    emptyProject.title should be("")
    emptyProject.description should be("")
  }

  it should "retain the customer ID when created with empty title and description" in {
    val customer = Customer(1, "John Doe", "john.doe@example.com", "1234567890", "123 Main St")
    val emptyProject = Project.empty(customer)

    emptyProject.customer_id should be(customer.id)
  }

  it should "allow setting values for ID, title, and description" in {
    val customer = Customer(1, "John Doe", "john.doe@example.com", "1234567890", "123 Main St")
    val project = Project.empty(customer).copy(id = 2, title = "Project X", description = "Description of Project X")

    project.id should be(2)
    project.title should be("Project X")
    project.description should be("Description of Project X")
  }
}
