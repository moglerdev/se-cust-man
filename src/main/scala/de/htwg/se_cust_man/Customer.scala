package de.htwg.se_cust_man

import java.util.Date

case class Customer(
    id: Int,
    firstname: String,
    lastname: String,
    birthdate: Date,
    telNo: String,
    email: String,
    address: String) {
        override def toString(): String = {
            s"Customer($id, $firstname, $lastname)"
        }
    }

def openCustomer(id: Int, customers: List[Customer]): Customer = {
    customers.find(c => c.id == id) match
        case Some(customer) => customer
        case None => Customer(id, "", "", new Date(), "", "", "");
}

def existsCustomer(id: Int, customers: List[Customer]) : Boolean = {
    customers.count(c => c.id == id) > 0;
}

def deleteCustomer(
    id: Int,
    customers: List[Customer]
): List[Customer] = {
    customers.filter(c => c.id != id)
}

def setCustomer(customer: Customer, customers: List[Customer]) : List[Customer] = {
    val d = deleteCustomer(customer.id, customers)
    d.appended(customer)
}