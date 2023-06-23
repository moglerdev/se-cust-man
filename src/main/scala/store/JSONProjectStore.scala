package de.htwg.scm
package store

import de.htwg.scm.models.Project
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._
import java.nio.file.{Files, Paths}

import scala.collection.mutable.ListBuffer

class JSONProjectStore extends IStore[Project] {
  private val filePath: String = "projects.json"
  private var projects: ListBuffer[Project] = readProjectsFromFile()

  private def readProjectsFromFile(): ListBuffer[Project] = {
    if (Files.exists(Paths.get(filePath))) {
      val fileContent = new String(Files.readAllBytes(Paths.get(filePath)))
      decode[ListBuffer[Project]](fileContent) match {
        case Right(decodedProjects) => decodedProjects
        case Left(error) =>
          println(s"Error decoding projects from JSON: $error")
          ListBuffer.empty[Project]
      }
    } else {
      ListBuffer.empty[Project]
    }
  }

  private def saveProjectsToFile(): Unit = {
    val jsonProjects = projects.asJson
    Files.write(Paths.get(filePath), jsonProjects.noSpaces.getBytes)
  }

  override def create(model: Project): Int = {
    val newId = getLastId + 1
    val projectWithId = model.copy(id = newId)
    projects += projectWithId
    saveProjectsToFile()
    newId
  }

  override def read(id: Int): Option[Project] = {
    projects.find(_.id == id)
  }

  override def update(id: Int, model: Project): Int = {
    val index = projects.indexWhere(_.id == id)
    if (index != -1) {
      val updatedProject = model.copy(id = id)
      projects.update(index, updatedProject)
      saveProjectsToFile()
      1 // indicates success
    } else {
      0 // indicates failure
    }
  }

  override def delete(id: Int): Int = {
    val index = projects.indexWhere(_.id == id)
    if (index != -1) {
      projects.remove(index)
      saveProjectsToFile()
      1 // indicates success
    } else {
      0 // indicates failure
    }
  }

  override def getAll: List[Project] = {
    projects.toList
  }

  override def getLastId: Int = {
    if (projects.isEmpty) {
      0
    } else {
      projects.map(_.id).max
    }
  }
}
