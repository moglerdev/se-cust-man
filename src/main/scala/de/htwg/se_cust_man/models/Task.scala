package de.htwg.se_cust_man.models

case class TaskItem(id: Long, task: Task, name: String, description: String, assigned: User, time: Int) extends DataModel


case class Task(id: Long, customer: Customer, items: List[TaskItem]) extends DataModel

