package de.htwg.se_cust_man.models

import java.util.Date

case class Invoice (id: Int, created: Date, paymentDate: Date, items: List[InvoiceItem])

case class InvoiceItem (id: Int, invoice: Invoice, price: Double, amount: Double, sum: Double)
