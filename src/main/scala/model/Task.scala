package de.htwg.scm
package model

import scala.reflect.runtime.universe.Try
import scala.util.matching.Regex

case class Task(id: Int, project_id: Int, title: String, description: String)

object Task {
  def empty(project: Project): Task = Task(-1, project.id, "", "")

  def apply(args: String): Task = {
    val regex: Regex =
      """-i\s+(\d+)|-d\s+(.*)|-t\s+(.*)|-p\s+(\d+)""".r

    var id: Option[Int] = None
    var description: Option[String] = None
    var title: Option[String] = None
    var projectId: Option[Int] = None

    args.split("\\s+").foreach {
      case regex(idStr, _, _, _) if id.isEmpty =>
        id = Some(idStr.toInt)
      case regex(_, descriptionValue, _, _) if description.isEmpty =>
        description = Some(descriptionValue)
      case regex(_, _, titleValue, _) if title.isEmpty =>
        title = Some(titleValue)
      case regex(_, _, _, projectIdStr) if projectId.isEmpty =>
        projectId = Some(projectIdStr.toInt)
      case _ => // Ignore unrecognized arguments
    }

    val task = for {
      taskId <- id
      taskDescription <- description
      taskTitle <- title
      projectId <- projectId
    } yield Task(taskId, projectId, taskTitle, taskDescription)

    task.getOrElse(throw new IllegalArgumentException("Failed to parse arguments into a Task"))
  }


}