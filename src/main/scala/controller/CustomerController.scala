package de.htwg.scm
package controller
import model.{Customer, Project, Task}

import com.google.inject.{Guice, Inject, Injector}
import de.htwg.scm.store.*
import net.codingwell.scalaguice.InjectorExtensions.*

trait ICustomerController extends IModelController[Customer] {
  def getByProject(project: Project): Option[Customer]
  def filter(name: Option[String], email: Option[String], phone: Option[String]): List[Customer]
}


class CustomerController @Inject()(store: ICustomerStore) extends ModelController[Customer](store) with ICustomerController {

  def getByProject(project: Project): Option[Customer] = {
    store.getAll.find(_.id == project.customer_id)
  }

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
