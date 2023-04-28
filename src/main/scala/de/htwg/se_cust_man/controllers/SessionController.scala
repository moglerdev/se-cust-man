package de.htwg.se_cust_man.controllers

import de.htwg.se_cust_man.Subject
import de.htwg.se_cust_man.models.User

class SessionController extends Subject {
  private var session: Option[User] = None

  def signIn(username: String, password: String): Boolean = {
    val user = if (username == "admin" && password == "admin") {
      Some(User("admin", "admin"))
    } else {
      None
    }
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

  def getUsername: Option[String] = {
    session match {
      case Some(user) => Some(user.username)
      case None => None
    }
  }

  def isSignedIn: Boolean = {
    session match {
      case Some(_) => true
      case None => false
    }
  }
}
