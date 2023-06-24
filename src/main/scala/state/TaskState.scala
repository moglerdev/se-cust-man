package de.htwg.scm
package state

import model.Task

class TaskState extends IState[Task] {
  private var task: Option[Task] = None

  override def set(model: Option[Task]): TaskState = {
    task = model
    this
  }

  override def get: Task = task.get

  override def option: Option[Task] = task

  override def isDefined: Boolean = task.isDefined

  override def isEmpty: Boolean = task.isEmpty
}
