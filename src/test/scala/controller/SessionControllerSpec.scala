package controller

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import de.htwg.scm.controller.SessionController

class SessionControllerSpec extends AnyFlatSpec with Matchers {

  "A SessionController" should "sign in a user with correct credentials" in {
    val controller = new SessionController

    // Sign in with correct credentials
    val signInResult = controller.signIn("user", "pwd")

    // Verify that the sign-in is successful
    signInResult should be(true)
    controller.isSignedIn should be(true)
  }

  it should "not sign in a user with incorrect credentials" in {
    val controller = new SessionController

    // Sign in with incorrect credentials
    val signInResult = controller.signIn("user", "wrongpwd")

    // Verify that the sign-in fails
    signInResult should be(false)
    controller.isSignedIn should be(false)
  }

  it should "sign out the user" in {
    val controller = new SessionController

    // Sign in first
    controller.signIn("user", "pwd")

    // Sign out
    val signOutResult = controller.signOut()

    // Verify that the sign-out is successful
    signOutResult should be(true)
    controller.isSignedIn should be(false)
  }
}
