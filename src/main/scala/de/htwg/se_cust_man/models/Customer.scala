package de.htwg.se_cust_man.models

case class Address(id: Long, street: String, city: String, zip: String, country: String) extends DataModel


case class Customer(id: Long, firstname: String, lastname: String, address: Address, phone: String, email: String, notes: String) extends DataModel
