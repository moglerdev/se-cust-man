package de.htwg.se_cust_man.controllers

import de.htwg.se_cust_man.models.User

import scala.io.StdIn

val users = List(
  User("admin", "admin"),
  User("mogler", "mein_password"),
  User("dennis", "sehr sicher"),
)

class UserController {
  def trySignIn(username: String, password: String): Boolean = {
    users.exists(user => user.username == username && user.password == password)
  }

  def signInPrompt(): User = {
    User(
      StdIn.readLine("username >>"),
      StdIn.readLine("password >>")
    )
  }
}


