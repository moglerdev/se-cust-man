package de.htwg.scm
package model

import scala.reflect.runtime.universe.Try
import scala.util.matching.Regex

case class Task(id: Int, project_id: Int, title: String, description: String)

object Task {
  def empty(project: Project): Task = Task(-1, project.id, "", "")

  def apply(args: String): Task = {
    val idReg = """-i\s+(\d+)""".r
    val titleReg = """-t\s+([^-]*)""".r
    val descriptionReg = """-d\s+(.*)""".r
    val projectIdReg = """-p\s+(\d+)""".r

    var id: Option[Int] = None
    var description: Option[String] = None
    var title: Option[String] = None
    var projectId: Option[Int] = None

    // Extract values from command line arguments
    idReg.findFirstMatchIn(args).foreach(m => id = Some(m.group(1).toInt))
    titleReg.findFirstMatchIn(args).foreach(m => title = Some(m.group(1)))
    descriptionReg.findFirstMatchIn(args).foreach(m => description = Some(m.group(1)))
    projectIdReg.findFirstMatchIn(args).foreach(m => projectId = Some(m.group(1).toInt))

    Task(id.getOrElse(-1), projectId.getOrElse(-1), title.map(_.trim).getOrElse(""), description.map(_.trim).getOrElse(""))
  }
}

trait ITaskBuilder {
  def setId(id: Int): ITaskBuilder
  def setTitle(title: String): ITaskBuilder
  def setProject(id: Int): ITaskBuilder
  def setProject(project: Project): ITaskBuilder
  def setDescription(description: String): ITaskBuilder
  def build(): Task
}

class TaskBuilder extends ITaskBuilder {
  private var id: Int = -1
  private var title: String = ""
  private var description: String = ""
  private var project_id: Int = -1

  override def setId(id: Int): ITaskBuilder = {
    this.id = id
    this
  }

  override def setTitle(title: String): ITaskBuilder = {
    this.title = title
    this
  }

  override def setProject(project: Project): ITaskBuilder = {
    this.project_id = project.id
    this
  }

  override def setDescription(description: String): ITaskBuilder = {
    this.description = description
    this
  }

  override def setProject(id: Int): ITaskBuilder = {
    this.project_id = id
    this
  }

  override def build(): Task = {
    Task(id, project_id, title, description)
  }
}
