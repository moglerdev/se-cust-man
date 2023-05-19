package de.htwg.se_cust_man

import java.sql.Timestamp
import java.util.Date

// ------------------------------------------------------------------------
// Models 

case class Account(id: Int, username: String, email: String, hashedPassword: String, name: String) {
    def checkPassword(password: String): Boolean = {
        val md = java.security.MessageDigest.getInstance("SHA-256")
        val hashedPassword = md.digest(password.getBytes("UTF-8")).map("%02x".format(_)).mkString
        hashedPassword == this.hashedPassword
    }
}

// Bridge with Customer ?
case class Address(id: Int, street: String, zip: String, city: String, isoCode: String)

case class Customer(id: Int, name: String, addressId: Int, email: String, phone: String)

case class Project(id: Int, title: String, description: String, deadline: Date, customerId: Int)

// Bridge with Project ?
case class Task(id: Int, title: String, description: String, estimatedTime: Double, requiredTime: Double, projectId: Int)


case class History(id: Int, objectName: String, objectId: Int, action: Char, accountId: Int, timestamp: Timestamp)

// Bridge with History?
case class HistoryChange(id: Int, historyId: Int, fieldName: String, oldValue: String)
