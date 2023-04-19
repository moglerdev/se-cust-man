package de.htwg.se_cust_man.models


enum ESubject {
  case Customer, Address, Contact, Product, Order, OrderItem
}

enum ECommands {
  case Add, Delete, Edit, Search, Exit
}

case class Command(subject: ESubject, command: ECommands, params: List[String] = List())

