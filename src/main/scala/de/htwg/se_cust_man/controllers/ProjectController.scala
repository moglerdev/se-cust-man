package de.htwg.se_cust_man.controllers

import de.htwg.se_cust_man.Subject
import de.htwg.se_cust_man.models.{Project, Customer}

class ProjectController extends Subject {

  def getProjects: Vector[Project] = DBProjects.value

  def addProject(project: Project): Unit = {
    DBProjects.add(project)
    notifyObservers()
  }

}
