import org.scalatest.flatspec._
import org.scalatest.matchers._
import de.htwg.se_cust_man._
import java.util.Date


class ParameterSpec extends AnyFlatSpec with should.Matchers {
    "Parameter" should "return a string" in {
        val parameters = List(
            Parameter("username", List("--username", "-u" ), "Username", false, ""),
            Parameter("password", List( "--password", "-p" ), "Password", false, ""),
            Parameter("help", List( "--help", "-h" ), "Show this help", false, "")
        )
        val args = List("--username", "user", "--password", "pwd")
        val result = parseParameters(args, parameters)
        result(0).value should be ("user")
        result(1).value should be ("pwd")
        result(2).value should be ("")
    }
    
}