package de.htwg.scm
package store

import java.io.{File, PrintWriter}
import scala.xml.XML
import de.htwg.scm.models.{Project, Task}

class XMLTaskStore extends IStore[Task] {
  private val xmlFilePath: String = "store/tasks.xml"

  override def create(model: Task): Int = {
    val tasks = loadTasksFromXML()
    val updatedTasks = tasks :+ model
    saveTasksToXML(updatedTasks)
    model.id
  }

  override def read(id: Int): Option[Task] = {
    val tasks = loadTasksFromXML()
    tasks.find(_.id == id)
  }

  override def update(id: Int, model: Task): Int = {
    val tasks = loadTasksFromXML()
    val updatedTasks = tasks.map(t => if (t.id == id) model else t)
    saveTasksToXML(updatedTasks)
    id
  }

  override def delete(id: Int): Int = {
    val tasks = loadTasksFromXML()
    val updatedTasks = tasks.filterNot(_.id == id)
    saveTasksToXML(updatedTasks)
    id
  }

  override def getAll: List[Task] = {
    loadTasksFromXML()
  }

  override def getLastId: Int = {
    val tasks = loadTasksFromXML()
    if (tasks.nonEmpty)
      tasks.map(_.id).max
    else
      -1
  }

  private def loadTasksFromXML(): List[Task] = {
    val file = new File(xmlFilePath)
    if (file.exists()) {
      val xml = XML.loadFile(file)
      (xml \ "task").map(parseTaskFromXML).toList
    } else {
      List.empty
    }
  }

  private def parseTaskFromXML(node: scala.xml.Node): Task = {
    Task(
      (node \ "id").text.toInt,
      (node \ "project_id").text.toInt,
      (node \ "title").text,
      (node \ "description").text
    )
  }

  private def saveTasksToXML(tasks: List[Task]): Unit = {
    val xml =
      <tasks>
        {tasks.map(taskToXML)}
      </tasks>
    val pw = new PrintWriter(new File(xmlFilePath))
    pw.write(xml.toString())
    pw.close()
  }

  private def taskToXML(task: Task): scala.xml.Node = {
    <task>
      <id>{task.id}</id>
      <project_id>{task.project_id}</project_id>
      <title>{task.title}</title>
      <description>{task.description}</description>
    </task>
  }
}
