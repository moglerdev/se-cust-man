package de.htwg.se_cust_man

case class InvoiceItem(
    id: Int,
    invoiceId: Int,
    description: String,
    price: Int,
    amount: Int,
    sum: Int
)

case class Invoice(
    id: Int,
    customerId: Int,
    items: List[InvoiceItem],
) {
    def sum() : Int = {
        val mappedSum = items.map(_.sum)
        mappedSum.foldLeft(0)(_ + _)
    }
}

def getInvoiceByCustomer(customer: Customer, invoices: List[Invoice])= {
    invoices.filter(p => p.customerId == customer.id);
}

def deleteInvoice(invoice: Invoice, invoices: List[Invoice]) = {
    invoices.filter(p => p.id != invoice.id);
}

def setInvoice(invoice: Invoice, invoices: List[Invoice]) = {
    val d = deleteInvoice(invoice, invoices)
    d.appended(invoice)
}