package de.htwg.se_cust_man.controllers

import de.htwg.se_cust_man.Subject
import de.htwg.se_cust_man.cli.Cli
import de.htwg.se_cust_man.models.Project

object DBProjects {
  def add(project: Project): Int = {
    val newId = DBProjects.value.length
    DBProjects.value = DBProjects.value :+ Project(id = newId, project.title)
    newId
  }

  var value : Vector[Project] = Vector(
    Project(0, "Se Cust Man"),
    Project(1, "Rechnungen")
  )
}
class EditorProjectController extends Subject{
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
        case "UPDATE" => DBProjects.value = DBProjects.value.map(x => {
          if (x.id == r.id) r
          else x
        })
        case "NEW" => DBProjects.add(r)
        case "DELETE" => DBProjects.value = DBProjects.value.filter(x => x.id != r.id)
        case _ => {}
    }
    notifyObservers()
  }
  Cli.subscribe(this.onMessage)

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
      DBProjects.add(cust)
      Cli.send(s"Project::NEW::${cust.toCSV}")
    } else {
      DBProjects.value = DBProjects.value.map(x => {
        if (x.id == cust.id) cust
        else x
      })
      Cli.send(s"Project::UPDATE::${cust.toCSV}")
    }
    if (closeAfter) {
      closeProject()
    }
    true
  }

  def createProject(): Option[Project] = {
    val res = openProject
    openProject = Some(Project(-1, ""))
    isNew = true
    notifyObservers()
    res
  }

  def openProject(id: Int): Option[Project] = {
    isNew = false
    openProject = DBProjects.value.find(x => x.id == id)
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
