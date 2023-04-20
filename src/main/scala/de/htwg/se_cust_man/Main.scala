package de.htwg.se_cust_man

import de.htwg.se_cust_man.controllers.UserController
import de.htwg.se_cust_man.tui.TuiController
import de.htwg.se_cust_man.models.User

// $COVERAGE-OFF$

def signIn(): Option[User] = {
  val authCtrl = new UserController()
  var user = authCtrl.signInPrompt();
  while (!authCtrl.trySignIn(user.username, user.password)) {
    println("Wrong username or password (Ctrl+C or sometimes Ctrl+Z to exit)")
    user = authCtrl.signInPrompt();
  }
  println(">>> Welcome " + user.username + " <<<")
  Some(user)
}

@main
def start(): Unit = {

  signIn() match
    case Some(sessionUser) => {
      val tuiCtrl = new TuiController(sessionUser)
      tuiCtrl.start()
      println(">>> Welcome " + sessionUser.username + " <<<")
    }
    case None => println(">>> No user signed in <<<")

}
// $COVERAGE-ON$