import org.scalatest.flatspec._
import org.scalatest.matchers._
import de.htwg.se_cust_man._
import java.util.Date


class InvoiceSpec extends AnyFlatSpec with should.Matchers {
    "Invoice sum" should "return 0" in {
        val invoice = new Invoice(1, 1, List())
        invoice.sum() should be (0)
    }
    "Invoice sum" should "return 10" in {
        val invoiceItem = new InvoiceItem(1, 1, "Test", 10, 1, 10)
        val invoice = new Invoice(1, 1, List(invoiceItem))
        invoice.sum() should be (10)
    }
    "Invoice sum" should "return 20" in {
        val invoiceItem = new InvoiceItem(1, 1, "Test", 10, 1, 10)
        val invoiceItem2 = new InvoiceItem(2, 1, "Test", 10, 1, 10)
        val invoice = new Invoice(1, 1, List(invoiceItem, invoiceItem2))
        invoice.sum() should be (20)
    }
    "Get Invoice by Customer" should "return a list with one invoice" in {
        val invoice = new Invoice(1, 1, List())
        val invoices = List(invoice)
        getInvoiceByCustomer(new Customer(1, "", "", new Date(), "", "", ""), invoices) should be (List(invoice))
    }
    "Get Invoice by Customer" should "return a list with two invoices" in {
        val invoice = new Invoice(1, 1, List())
        val invoice2 = new Invoice(2, 1, List())
        val invoices = List(invoice, invoice2)
        getInvoiceByCustomer(new Customer(1, "", "", new Date(), "", "", ""), invoices) should be (List(invoice, invoice2))
    }
    "Get Invoice by Customer" should "return a list with no invoices" in {
        val invoice = new Invoice(1, 1, List())
        val invoice2 = new Invoice(2, 1, List())
        val invoices = List(invoice, invoice2)
        getInvoiceByCustomer(new Customer(2, "", "", new Date(), "", "", ""), invoices) should be (List())
    }
    "Delete Invoice" should "return a list with one invoice" in {
        val invoice = new Invoice(1, 1, List())
        val invoices = List(invoice)
        deleteInvoice(invoice, invoices) should be (List())
    }
    "Set Invoice" should "return a list with one invoice" in {
        val invoice = new Invoice(1, 1, List())
        val invoices = List(invoice)
        val newInvoice = new Invoice(1, 1, List())
        setInvoice(newInvoice, invoices) should be (List(newInvoice))
    }
}