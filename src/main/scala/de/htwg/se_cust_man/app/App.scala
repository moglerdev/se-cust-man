package de.htwg.se_cust_man.app

import de.htwg.se_cust_man._
import de.htwg.se_cust_man.service.CustomerServiceSql

object App {
    @main
    def run(): Unit = {
        val service = new CustomerServiceSql()
        val customer = Customer(-1, "Test", 0, "mogler@mogler.dev", "+49 172 6261182")
        service.insertCustomer(customer)
        val s = service.getCustomers
        s.foreach(println)
    }
}
