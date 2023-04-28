package de.htwg.se_cust_man.controllers

import de.htwg.se_cust_man.Subject
import de.htwg.se_cust_man.models.User

var users = List(
  User("admin", "admin"),
  User("mogler", "mein_password"),
  User("dennis", "sehr sicher"),
)


class SessionController extends Subject {
  private var session: Option[User] = None

  def signIn(username: String, password: String): Boolean = {
    val user = users.find(p => p.username == username && p.password == password)
    if (user.isEmpty) {
      false
    } else {
      session = user
      notifyObservers()
      true
    }
  }

  def signOut (): Boolean = {
    session = None
    notifyObservers()
    true
  }

  def getUsername: String = {
    session match {
      case Some(user) => user.username
      case None => ""
    }
  }

  def isSignedIn: Boolean = {
    session match {
      case Some(_) => true
      case None => false
    }
  }
}
