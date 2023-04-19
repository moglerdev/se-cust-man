package de.htwg.se_cust_man.models
case class Permission(id: Long, name: String, read: Boolean, write: Boolean, delete: Boolean, execute: Boolean) extends DataModel

case class Group(id: Long, name: String, permissions: List[Permission]) extends DataModel

case class User (id: Long, username: String, password: String, email: String, group: Group) extends DataModel

