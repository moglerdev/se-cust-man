package de.htwg.scm
package store

import java.io.{File, PrintWriter}
import scala.xml.XML
import de.htwg.scm.models.Project

class XMLProjectStore extends IStore[Project] {
  private val xmlFilePath: String = "store/projects.xml"

  override def create(model: Project): Int = {
    val projects = loadProjectsFromXML()
    val updatedProjects = projects :+ model
    saveProjectsToXML(updatedProjects)
    model.id
  }

  override def read(id: Int): Option[Project] = {
    val projects = loadProjectsFromXML()
    projects.find(_.id == id)
  }

  override def update(id: Int, model: Project): Int = {
    val projects = loadProjectsFromXML()
    val updatedProjects = projects.map(p => if (p.id == id) model else p)
    saveProjectsToXML(updatedProjects)
    id
  }

  override def delete(id: Int): Int = {
    val projects = loadProjectsFromXML()
    val updatedProjects = projects.filterNot(_.id == id)
    saveProjectsToXML(updatedProjects)
    id
  }

  override def getAll: List[Project] = {
    loadProjectsFromXML()
  }

  override def getLastId: Int = {
    val projects = loadProjectsFromXML()
    if (projects.nonEmpty)
      projects.map(_.id).max
    else
      -1
  }

  private def loadProjectsFromXML(): List[Project] = {
    val file = new File(xmlFilePath)
    if (file.exists()) {
      val xml = XML.loadFile(file)
      (xml \ "project").map(parseProjectFromXML).toList
    } else {
      List.empty
    }
  }

  private def parseProjectFromXML(node: scala.xml.Node): Project = {
    Project(
      (node \ "id").text.toInt,
      (node \ "customer_id").text.toInt,
      (node \ "title").text,
      (node \ "description").text
    )
  }

  private def saveProjectsToXML(projects: List[Project]): Unit = {
    val xml =
      <projects>
        {projects.map(projectToXML)}
      </projects>
    val pw = new PrintWriter(new File(xmlFilePath))
    pw.write(xml.toString())
    pw.close()
  }

  private def projectToXML(project: Project): scala.xml.Node = {
    <project>
      <id>{project.id}</id>
      <customer_id>{project.customer_id}</customer_id>
      <title>{project.title}</title>
      <description>{project.description}</description>
    </project>
  }
}
