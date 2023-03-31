import org.scalatest.flatspec._
import org.scalatest.matchers._
import de.htwg.se_cust_man._
import java.util.Date


class MainSpec extends AnyFlatSpec with should.Matchers {
    "main function" should "print" in {
        main(Array()) should be (())
    }
    "test test" should "fail" in {
        1 should be (2)
    }
}