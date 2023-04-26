package de.htwg.se_cust_man.controllers

import de.htwg.se_cust_man.Subject
import de.htwg.se_cust_man.models.User

import scala.io.StdIn

var users = List(
  User("admin", "admin"),
  User("mogler", "mein_password"),
  User("dennis", "sehr sicher"),
)

class UserController extends Subject {
  var session: Option[User] = None

  def signIn(username: String, password: String) : Boolean = {
    val user = getUser(username)
    if (user.isEmpty) {
      false
    } else {
      if (user.get.password == password) {
        session = user
        notifyObservers()
        true
      } else {
        false
      }
    }
  }

  def addUser(user: User) : Unit = {
    users = users :+ user
    notifyObservers()
  }

  def removeUser(user: User) : Unit = {
    users = users.filterNot(_ == user)
    notifyObservers()
  }

  def getUser(username: String): Option[User] = {
    users.find(_.username == username)
  }
}
