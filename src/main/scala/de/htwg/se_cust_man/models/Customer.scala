package de.htwg.se_cust_man.models

case class Customer(id: Long, name: String, address: String, phone: String, email: String) {
  override def toString: String = name + " [" + id + "]"
}