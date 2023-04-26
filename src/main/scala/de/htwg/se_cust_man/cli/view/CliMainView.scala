package de.htwg.se_cust_man.cli.view

import de.htwg.se_cust_man.controllers.UserController
import de.htwg.se_cust_man.models.User

class CliMainView extends CliView {
  val cltr = new UserController
  cltr.subscribe(this)

  var isSignedIn: Boolean = cltr.session.isDefined

  override def onNotify(): Unit = {
    isSignedIn = cltr.session.isDefined
  }

  override def close(): Unit = {
    cltr.unsubscribe(this)
  }

  override def render(): Unit = {
    while (true) {
      val signInView = new CliSignInView(cltr)
      signInView.render()
    }
  }

}
