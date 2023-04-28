package de.htwg.se_cust_man.models

object Customer {
  def fromCSV(csvRow: String): Customer = {
    val values = csvRow.split(";")
    Customer(values(0).toLong, values(1), values(2), values(3), values(4))
  }
}

case class Customer(id: Long, name: String, address: String, phone: String, email: String) {
  override def toString: String =
    s"name: ${name}; address: ${address}; phone: ${phone}; email: ${email}"

  def toCSV: String =
    s"${id};${name};${address};${phone};${email}"
}