package de.htwg.scm
package controller

// Controller for managing user sessions
trait ISessionController {
  def signIn(username: String, password: String): Boolean
  def signOut: Boolean
  def isSignedIn: Boolean
}

// Implementation of the session controller
class SessionController extends Publisher with ISessionController {
  var session = false

  // Signs in a user with the provided username and password
  override def signIn(username: String, password: String): Boolean = {
    // TODO: Implement database authentication
    if (username == "user" && password == "pwd") {
      session = true
      publish()
      true
    } else false
  }

  // Signs out the current user
  override def signOut: Boolean = {
    session = false
    publish()
    true
  }

  // Checks if a user is currently signed in
  override def isSignedIn: Boolean = session
}
