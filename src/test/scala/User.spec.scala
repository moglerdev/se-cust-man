import org.scalatest.flatspec._
import org.scalatest.matchers._
import de.htwg.se_cust_man._
import java.util.Date


class UserSpec extends AnyFlatSpec with should.Matchers {
    "Check Credentials" should "return boolean" in {
        val user = User("hello", "world", "Hello World", "", Role.Admin)
        val users = List(user)
        checkCredentials("hello", "world", users) should be (true)
        checkCredentials("hello", "world2", users) should be (false)
    }

    "login" should "return user" in {
        val user = User("hello", "world", "Hello World", "", Role.Admin)
        val users = List(user)
        login("hello", "world", users) should be (user)
    }

    "deleteUser" should "remove user from list" in {
        val user = User("hello", "world", "Hello World", "", Role.Admin)
        val users = List(user)
        deleteUser(User("", "","","", Role.User), users) should be (List(user))
        deleteUser(user, users) should be (List())
    }

    "setUser" should "new updated list" in {
        val user = User("hello", "world", "Hello World", "", Role.Admin)
        val users = List(user)
        val updateUser = User("hello", "new password", "Was Geht", "", Role.Admin)
        setUser(updateUser, users) should be (List(updateUser))
        val newUser = User("new_user", "new password", "Was Geht", "", Role.Admin)
        setUser(newUser, users) should be (List(user, newUser))
    }
}