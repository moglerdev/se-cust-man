package de.htwg.scm
package model

case class Task(id: Int, project_id: Int, title: String, description: String)

object Task {
  def empty(project: Project) = Task(-1, project.id, "", "")
}