package de.htwg.se_cust_man.controllers

import de.htwg.se_cust_man.Subject
import de.htwg.se_cust_man.models.{Project, Customer}

class ProjectController(customer: Customer) extends Subject {
  var projects = Vector[Project]()

  def addProject(project: Project) = {
    projects = projects :+ project
    notifyObservers()
  }

}
