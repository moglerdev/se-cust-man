package de.htwg.scm
package controller
import model.{Customer, Project, Task}

import com.google.inject.{Guice, Injector}
import de.htwg.scm.store._
import net.codingwell.scalaguice.InjectorExtensions.*

// Controller for managing customers
class CustomerController(store: ICustomerStore) extends ModelController[Customer](store) with ICustomerController {

  // Retrieves the customer associated with the specified project
  def getByProject(project: Project): Option[Customer] = {
    store.getAll.find(_.id == project.customer_id)
  }

  // Filters the customers based on the specified criteria
  override def filter(name: Option[String], email: Option[String], phone: Option[String]): List[Customer] = {
    store.getAll.filter(c => {
      val nameFilter = name match {
        case Some(n) => c.name.contains(n)
        case None => true
      }
      val emailFilter = email match {
        case Some(e) => c.email.contains(e)
        case None => true
      }
      val phoneFilter = phone match {
        case Some(p) => c.phone.contains(p)
        case None => true
      }
      nameFilter && emailFilter && phoneFilter
    })
  }
}
