package de.htwg.se_cust_man


case class Customer(name: String, address: String, email: String, phone: String)

case class CustomerEditor(customer: Customer);
