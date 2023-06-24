package de.htwg.scm
package state

import model.{Project, Customer}

class ProjectState extends IState[Project] {
  private var project: Option[Project] = None

  override def set(model: Option[Project]): ProjectState = {
    project = model
    this
  }

  override def get: Project = project.get

  override def option: Option[Project] = project

  override def isDefined: Boolean = project.isDefined

  override def isEmpty: Boolean = project.isEmpty
}
