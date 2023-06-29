package de.htwg.scm
package view.tui

import com.google.inject.{Guice, Injector}
import net.codingwell.scalaguice.InjectorExtensions.*
import store.StoreModule
import view.tui.TuiModelView
import controller.{ControllerModule, ICustomerController, IProjectController, ITaskController}
import model.{Customer, Project}

import scala.util.{Failure, Success, Try}


class TuiCustomerView(customerController: ICustomerController) extends TuiModelView[Customer]("customer", customerController) with IObserver {
  val injector: Injector = Guice.createInjector(new ControllerModule, new StoreModule)
  val projectController: IProjectController = injector.instance[IProjectController]

  override protected def convertToModel(args: String): Try[Customer] = Try {
    Customer.apply(args)
  }

  override protected def handleSet(model: Customer): Try[String] = {
    if (model.id < 0) {
      // add task
      customerController.add(model)
      Success("Item added")
    } else {
      val found = customerController.get(model.id)
      if (found.isDefined) {
        // update task
        customerController.update(model.id, model)
        Success("Customer updated")
      } else {
        Failure(new IllegalArgumentException("Customer with id " + model.id + " not found, to add a customer dont use -i <id>"))
      }
    }
  }

  override protected def handleList(): Try[String] = {
    customerController.getAll.map(_.toString).mkString("\n") match {
      case "" => Success("No customers found")
      case tasks => Success(tasks)
    }
  }

  override protected def handleHelp(): Try[String] = {
    Success("set\t\t[-i <id>] [-n <name>] [-e <email>] [-p <phone>] [-a <address>]\t\t<-> if id is not set add a customer, else update customer\n" +
      "rm\t\t<id ...>\t\t<-> remove customers by id (whitespace separated)\n" +
      "open\t\t<id>\t\t<-> open customer (to access project)\n" +
      "ls\t\t<-> list customer\n" +
      "help\t<-> show this help")
  }

  override def handleCommand(cmd: String, args: String): Try[String] = {
    cmd match {
      case "open" =>
        Try(args.toInt) match {
          case Success(customerId) =>
            val customer = customerController.get(customerId)
            if (customer.isDefined) {
              setNext(new TuiProjectView(customer.get, projectController))
              Success("Customer opened")
            } else {
              Failure(new IllegalArgumentException("Customer with id " + customerId + " not found"))
            }
          case Failure(_) =>
            Failure(new IllegalArgumentException("Invalid customer ID: " + args))
        }
      case _ => super.handleCommand(cmd, args)
    }
  }


  override def published(): Unit = {
    print(handleList().get)
  }
}
