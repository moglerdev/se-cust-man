package de.htwg.se_cust_man.models

case class Address(street: String, city: String, zip: String, country: String)

case class Customer(cid: Int, firstname: String, lastname: String, address: Address, phone: String, email: String, notes: String)
