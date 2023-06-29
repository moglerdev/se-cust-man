package de.htwg.scm
package store

import model.Task
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._
import java.nio.file.{Files, Paths}

import scala.collection.mutable.ListBuffer

class JSONTaskStore extends ITaskStore {
  private val filePath: String = "tasks.json"
  private var tasks: ListBuffer[Task] = readTasksFromFile()

  private def readTasksFromFile(): ListBuffer[Task] = {
    if (Files.exists(Paths.get(filePath))) {
      val fileContent = new String(Files.readAllBytes(Paths.get(filePath)))
      decode[ListBuffer[Task]](fileContent) match {
        case Right(decodedTasks) => decodedTasks
        case Left(error) =>
          println(s"Error decoding tasks from JSON: $error")
          ListBuffer.empty[Task]
      }
    } else {
      ListBuffer.empty[Task]
    }
  }

  private def saveTasksToFile(): Unit = {
    val jsonTasks = tasks.asJson
    Files.write(Paths.get(filePath), jsonTasks.noSpaces.getBytes)
  }

  override def create(model: Task): Int = {
    val newId = getLastId + 1
    val taskWithId = model.copy(id = newId)
    tasks += taskWithId
    saveTasksToFile()
    newId
  }

  override def read(id: Int): Option[Task] = {
    tasks.find(_.id == id)
  }

  override def update(id: Int, model: Task): Int = {
    val index = tasks.indexWhere(_.id == id)
    if (index != -1) {
      val updatedTask = model.copy(id = id)
      tasks.update(index, updatedTask)
      saveTasksToFile()
      1 // indicates success
    } else {
      0 // indicates failure
    }
  }

  override def delete(id: Int): Int = {
    val index = tasks.indexWhere(_.id == id)
    if (index != -1) {
      tasks.remove(index)
      saveTasksToFile()
      1 // indicates success
    } else {
      0 // indicates failure
    }
  }

  override def delete(model: Task): Int = delete(model.id)

  override def getAll: List[Task] = {
    tasks.toList
  }

  override def getLastId: Int = {
    if (tasks.isEmpty) {
      0
    } else {
      tasks.map(_.id).max
    }
  }
}
