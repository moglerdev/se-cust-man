package de.htwg.scm
package state

import model.Project

class ProjectState extends IState[Project] {
  private var project: Option[Project] = None

  override def set(model: Option[Project]): ProjectState = {
    project = model
    this
  }

  override def get: Option[Project] = project
}
