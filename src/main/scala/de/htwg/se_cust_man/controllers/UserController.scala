package de.htwg.se_cust_man.controllers

import de.htwg.se_cust_man.Subject
import de.htwg.se_cust_man.models.User

import scala.io.StdIn

class UserController extends Subject {
  private var session: Option[User] = None


  def addUser(user: User) : Unit = {
    notifyObservers()
  }

  def removeUser(user: User) : Unit = {
    notifyObservers()
  }

  def getUser(username: String): Option[User] = {
    None
  }
}
