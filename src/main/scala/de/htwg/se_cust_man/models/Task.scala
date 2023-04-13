package de.htwg.se_cust_man.models

case class TaskItem(id: Long, name: String, description: String, assigned: User, time: Int)

case class Task(id: Long, customer: Customer, items: List[TaskItem])
