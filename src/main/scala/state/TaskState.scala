package de.htwg.scm
package state

import model.Task

class TaskState extends IState[Task] {
  private var project: Option[Task] = None

  override def set(model: Option[Task]): TaskState = {
    project = model
    this
  }

  override def get: Option[Task] = project
}
