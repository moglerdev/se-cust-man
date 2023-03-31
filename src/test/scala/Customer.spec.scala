import org.scalatest.flatspec._
import org.scalatest.matchers._
import de.htwg.se_cust_man._
import java.util.Date


class CustomerSpec extends AnyFlatSpec with should.Matchers {
    "Customer to String" should "return a string" in {
        val customer = new Customer(1, "John", "Doe", new Date(), "", "", "")
        customer.toString() should be ("Customer(1, John, Doe)")
    }
    "Open Customer" should "return a customer" in {
        val customer = new Customer(1, "John", "Doe", new Date(), "", "", "")
        val customers = List(customer)
        openCustomer(1, customers) should be (customer)
        openCustomer(3, customers) should be (new Customer(3, "", "", new Date(), "", "", ""))
    }
    "Delete Customer" should "return a list with one customer" in {
        val customer = new Customer(1, "John", "Doe", new Date(), "", "", "")
        val customers = List(customer)
        deleteCustomer(1, customers) should be (List())
    }
    "Delete Customer" should "return a list with two customers" in {
        
        val customer = new Customer(2, "John", "Doe", new Date(), "", "", "")
        val customer2 = new Customer(3, "John", "Doe", new Date(), "", "", "")
        val customers = List(customer, customer2)
        deleteCustomer(1, customers) should be (List(customer, customer2))
    }
    "Exists Customer" should "return true" in {
        
        val customer = new Customer(1, "John", "Doe", new Date(), "", "", "")
        val customers = List(customer)
        existsCustomer(1, customers) should be (true)
    }
    "Exists Customer" should "return false" in {
        
        val customer = new Customer(1, "John", "Doe", new Date(), "", "", "")
        val customers = List(customer)
        existsCustomer(2, customers) should be (false)
    }
    "Set Customer" should "return a list with one customer" in {
        val customer = new Customer(1, "John", "Doe", new Date(), "", "", "")
        val customers = List(customer)
        val newCustomer = new Customer(1, "Hello", "World", new Date(), "", "", "")
        setCustomer(newCustomer, customers) should be (List(newCustomer))
    }
    "Set Customer" should "return a list with two customers" in {
        
        val customer = new Customer(1, "John", "Doe", new Date(), "", "", "")
        val customer2 = new Customer(2, "World", "Doe", new Date(), "", "", "")
        val customers = List(customer, customer2)

        val newCustomer = new Customer(1, "Hello", "Doe", new Date(), "", "", "")

        setCustomer(newCustomer, customers) should be (List(customer2, newCustomer))
    }
}