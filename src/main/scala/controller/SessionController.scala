package de.htwg.scm
package controller

trait ISessionController {
  def signIn(username: String, password: String) : Boolean
  def signOut : Boolean
  def isSignedIn: Boolean
}

class SessionController extends Publisher with ISessionController {
  var session = false

  override def signIn(username: String, password: String): Boolean = {
    // TODO: Implement DB
    if (username == "user" && password == "pwd") {
      session = true
      notifySubscribers()
      true
    } else false
  }

  override def signOut: Boolean = {
    session = false
    notifySubscribers()
    true
  }

  override def isSignedIn: Boolean = session
}
