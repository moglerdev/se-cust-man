package de.htwg.se_cust_man


// ------------------------------------------------------------------------
// Proxy [3/4]
// https://refactoring.guru/design-patterns/proxy

trait CustomerService {
    def getCustomers: Vector[Customer]
    def getCustomerById(id: Int): Option[Customer]
    def getCustomerByName(name: String): Option[Customer]
    def removeCustomer(customer: Customer): Boolean
}

class CustomerServiceSql extends CustomerService {
    def getCustomers: Vector[Customer] = {
        Vector()
    }
    def getCustomerById(id: Int): Option[Customer] = {
        None
    }
    def getCustomerByName(name: String): Option[Customer] = {
        None
    }
    def removeCustomer(customer: Customer): Boolean = {
        false
    }
}

class CustomerServiceRest extends CustomerService {
    def getCustomers: Vector[Customer] = {
        Vector()
    }
    def getCustomerById(id: Int): Option[Customer] = {
        None
    }
    def getCustomerByName(name: String): Option[Customer] = {
        None
    }
    def removeCustomer(customer: Customer): Boolean = {
        false
    }
}
