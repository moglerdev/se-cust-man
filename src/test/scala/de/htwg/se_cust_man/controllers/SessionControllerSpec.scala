package de.htwg.se_cust_man.controllers

import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec

class SessionControllerSpec extends AnyWordSpec with Matchers {
  "Session Controller" should {
    "return a session" in {
      val sessionController = new SessionController
      sessionController.isSignedIn must be(false)
      sessionController.getUsername must be(None)
      sessionController.signIn("admin", "admin") must be (true)

      sessionController.getUsername must be(Some("admin"))

      sessionController.isSignedIn must be(true)

      sessionController.signOut()
      sessionController.isSignedIn must be(false)
      sessionController.signIn("xxx", "xx") must be(false)
      sessionController.isSignedIn must be(false)
    }
  }
}
