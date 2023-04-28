package de.htwg.se_cust_man.models

object Customer {
  def fromCSV(csvRow: String): Customer = {
    val values = csvRow.split(";")
    val id = values(0).toLong
    val name = values(1)
    val address = if (values.length > 2) values(2) else ""
    val phone = if (values.length > 3) values(3) else ""
    val email = if (values.length > 4) values(4) else ""
    Customer(id, name, address, phone, email)
  }
}

case class Customer(id: Long, name: String, address: String, phone: String, email: String) {
  override def toString: String =
    s"name: ${name}; address: ${address}; phone: ${phone}; email: ${email}"

  def toCSV: String =
    s"${id};${name};${address};${phone};${email}"
}