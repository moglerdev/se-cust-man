package de.htwg.se_cust_man

import java.util.Date

case class Customer(
    id: Int,
    firstname: String,
    lastname: String,
    birthdate: Date,
    telNo: String,
    email: String,
    address: String);

val __customers = List(
    Customer(51373, "Johann Carl Friedrich", "Gauß", new Date(1777, 4, 30), "+49 7836 2036",
    "johann.carl.friedrich@gauss.de",
    "Albanikirchhof 1A, 37073 Göttingen")
);

def openCustomer(id: Int, customers: List[Customer]): Customer | false = {
    customers.find(c => c.id == id) match
        case Some(customer) => customer
        case None => false
}

def existsCustomer(id: Int, customers: List[Customer]) : Boolean = {
    customers.count(c => c.id == id) > 0;
}

def setCustomer(customer: Customer, customers: List[Customer]) : List[Customer] = {
    val res = deleteCustomer(customer.id, customers);
    customers.appended(customer);
}

def searchCustomer(
    firstname: String,
    lastname: String,
    birthdate: Date,
    telNo: String,
    email: String,
    address: String,
    customers: List[Customer]
): List[Customer] = {
    customers.filter(c => c.firstname == firstname && c.lastname == lastname && c.birthdate == birthdate
    && c.telNo == telNo && c.email == email && c.address == address)
}

def deleteCustomer(
    id: Int,
    customers: List[Customer]
): List[Customer] = {
    customers.filter(c => c.id != id)
}