package de.htwg.se_cust_man.controllers

import de.htwg.se_cust_man.Subject
import de.htwg.se_cust_man.models.{Project, Customer}

class ProjectController(var projects: Vector[Project]) extends Subject {

  def getProjects: Vector[Project] = projects

  def getProjectById(id: Long): Option[Project] = projects.find(_.id == id)

  //def getProjectsByCustomer(customer: Customer): Vector[Project] = DBProjects.value.filter(_.id == customer.id)

  def removeProject(project: Project): Boolean = {
    projects = projects.filterNot(_ == project)
    notifyObservers()
    true
  }

  def getProjectByTitle(title: String): Option[Project] = projects.find(_.title == title)

  def addProject(project: Project): Boolean = {
    projects = projects :+ project
    notifyObservers()
    true
  }

}
