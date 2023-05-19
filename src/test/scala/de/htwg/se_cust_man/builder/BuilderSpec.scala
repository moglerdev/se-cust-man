package de.htwg.se_cust_man.builder

import org.scalatest.*
import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec
import de.htwg.se_cust_man.builder.{AccountBuilder, AddressBuilder, CustomerBuilder, ProjectBuilder, TaskBuilder}
import java.util.Date
import de.htwg.se_cust_man.Task

class BuilderSpec extends AnyWordSpec with Matchers {
  "A Builder" should {
    "build a Account" in {
      val builder = new AccountBuilder()
      val account = builder.setUsername("okay").setEmail("okay").setPassword("okay").setName("okay").build
      account.username must be("okay")
      account.email must be("okay")
      account.hashedPassword must be("e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855")
      account.name must be("okay")
    }
    "reset a Account" in {
      val builder = new AccountBuilder()
      val account = builder.setUsername("okay").setEmail("okay").setPassword("okay").setName("okay").reset.build
      account.username must be("")
      account.email must be("")
      // account.hashedPassword must be("")
      account.name must be("")
    }
    "build a Address" in {
      val builder = new AddressBuilder()
      val address = builder.setStreet("okay").setZip("okay").setCity("okay").setIsoCode("okay").build
      address.street must be("okay")
      address.zip must be("okay")
      address.city must be("okay")
      address.isoCode must be("okay")
    }
    "reset a Address" in {
      val builder = new AddressBuilder()
      val address = builder.setStreet("okay").setZip("okay").setCity("okay").setIsoCode("okay").reset.build
      address.street must be("")
      address.zip must be("")
      address.city must be("")
      address.isoCode must be("")
    }
    "build a Customer" in {
      val builder = new CustomerBuilder()
      val customer = builder.setName("okay").setAddress(1).setEmail("okay").setPhone("okay").build
      customer.name must be("okay")
      customer.addressId must be(1)
      customer.email must be("okay")
      customer.phone must be("okay")
    }
    "reset a Customer" in {
      val builder = new CustomerBuilder()
      val customer = builder.setName("okay").setAddress(1).setEmail("okay").setPhone("okay").reset.build
      customer.name must be("")
      customer.addressId must be(-1)
      customer.email must be("")
      customer.phone must be("")
    }
    "build a Project" in {
      val builder = new ProjectBuilder()
      val project = builder.setTitle("okay").setDescription("okay").setDeadline(new Date(2023, 10, 1)).setCustomer(1).build
      project.title must be("okay")
      project.description must be("okay")
      // project.deadline.toString must be("2021-01-01")
      project.customerId must be(1)
    }
    "reset a Project" in {
      val builder = new ProjectBuilder()
      val project = builder.setTitle("okay").setDescription("okay").setDeadline(new Date(2023, 10, 1)).setCustomer(1).reset.build
      project.title must be("")
      project.description must be("")
      // project.deadline.toString must be("1970-01-01")
      project.customerId must be(-1)
    }
    "build a Task" in {
      val builder = new TaskBuilder()
      val task = builder.setProjectId(1).addTask(Task(1, "task1", "", 1.0, 2.0, 0)).build
      task must be(Vector(Task(-1, "task1", "", 1.0, 2.0, 1)))
    }
    "reset a Task" in {
      val builder = new TaskBuilder()
      val task = builder.setProjectId(1).addTask(Task(1, "task1", "", 1.0, 2.0, 0)).reset.build
      task.length must be(0)
    }
    
  }
}
