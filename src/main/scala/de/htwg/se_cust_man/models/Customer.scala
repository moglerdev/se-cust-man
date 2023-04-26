package de.htwg.se_cust_man.models

case class Task(id: Long, title: String, content: String)
case class Project(id: Long, title: String)
case class Customer(id: Long, name: String, address: String, phone: String, email: String)
