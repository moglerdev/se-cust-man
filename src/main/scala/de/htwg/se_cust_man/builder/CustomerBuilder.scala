package de.htwg.se_cust_man.builder

import de.htwg.se_cust_man.{Customer, Address}

class CustomerBuilder extends Builder[Customer] {
    var name: String = ""
    var address: Int = -1
    var email: String = ""
    var phone: String = ""

    def setName(name: String): CustomerBuilder = {
        this.name = name
        this
    }
    def setAddress(address: Int): CustomerBuilder = {
        this.address = address
        this
    }
    def setEmail(email: String): CustomerBuilder = {
        this.email = email
        this
    }
    def setPhone(phone: String): CustomerBuilder = {
        this.phone = phone
        this
    }
    def build = Customer(-1, name, address, email, phone)
    def reset = new CustomerBuilder
}

class AddressBuilder extends Builder[Address] {
    var street: String = ""
    var zip: String = ""
    var city: String = ""
    var isoCode: String = ""

    def setStreet(street: String): AddressBuilder = {
        this.street = street
        this
    }
    def setZip(zip: String): AddressBuilder = {
        this.zip = zip
        this
    }
    def setCity(city: String): AddressBuilder = {
        this.city = city
        this
    }
    def setIsoCode(isoCode: String): AddressBuilder = {
        this.isoCode = isoCode
        this
    }
    def build = Address(-1, street, zip, city, isoCode)
    def reset = new AddressBuilder
}

