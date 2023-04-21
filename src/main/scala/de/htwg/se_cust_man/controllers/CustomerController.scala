package de.htwg.se_cust_man.controllers

import de.htwg.se_cust_man.models.Customer
import scala.io.StdIn

object CustomerController {
  def createPrompt(inName: String = ""): Customer = {

    val name = if(inName == "") StdIn.readLine("Enter customer name: ") else inName
    val address = StdIn.readLine("Enter customer address: ")
    val phone = StdIn.readLine("Enter customer phone: ")
    val email = StdIn.readLine("Enter customer email: ")
    Customer(name, address, phone, email)
  }
}
