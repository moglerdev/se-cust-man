package de.htwg.se_cust_man.controllers

import de.htwg.se_cust_man.Subject
import de.htwg.se_cust_man.cli.Cli
import de.htwg.se_cust_man.models.Project


class EditorProjectController(var projects: Vector[Project]) extends Subject{
  private var openProject: Option[Project] = None
  private var isNew: Boolean = false

  def onMessage(msg: String): Unit = {
    val objs = msg.split("::")
    if (objs.head.startsWith("PROJECT")) {
      val r = Project.fromCSV(objs.last)
      if (openProject.isDefined && r.id == openProject.get.id) {
        openProject = Some(r)
        isNew = false
      }
      objs(1) match
        case "UPDATE" => projects = projects.map(x => {
          if (x.id == r.id) r
          else x
        })
        case "NEW" => projects = projects :+ r
        case "DELETE" => projects = projects.filterNot(x => x.id == r.id)
        case _ => {}
    }
    notifyObservers()
  }
  // TODO: Auslagern
  // Cli.subscribe(this.onMessage)

  def getOpenProject: Option[Project] = openProject

  def isOpen: Boolean = openProject.isDefined || isNew
  def isNewProject: Boolean = isNew

  def updateValues(key: String, value: String): Boolean = {
    if (!isOpen) return false
    val cus = openProject.get
    openProject = Some(key match
      case "title" => cus.copy(title = value)
      case _ => return false
    )
    notifyObservers()
    true
  }

  def saveProject(closeAfter: Boolean = false): Boolean = {
    if (openProject.isEmpty) return false
    val cust = openProject.get
    if (isNew) {
      projects = projects :+ cust
      Cli.send(s"PROJECT::NEW::${cust.toCSV}")
    } else {
      projects = projects.map(x => {
        if (x.id == cust.id) cust
        else x
      })
      Cli.send(s"PROJECT::UPDATE::${cust.toCSV}")
    }
    if (closeAfter) {
      closeProject()
    }
    true
  }

  def createProject(project: Option[Project] = None): Option[Project] = {
    val res = openProject
    openProject = Some(project.getOrElse(Project.empty))
    isNew = true
    notifyObservers()
    res
  }

  def openProject(id: Long): Option[Project] = {
    isNew = false
    openProject = projects.find(x => x.id == id)
    notifyObservers()
    openProject
  }

  def closeProject(): Option[Project] = {
    isNew = false
    val result = openProject
    openProject = None
    notifyObservers()
    result
  }
}
