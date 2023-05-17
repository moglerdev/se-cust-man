package de.htwg.se_cust_man

import java.sql.Timestamp
import java.sql.Date

// ------------------------------------------------------------------------
// Models 

// Bridge with Customer ?
case class Address(id: Int, street: String, zip: String, city: String, isoCode: String)

case class Customer(id: Int, name: String, address_id: Int, email: String, phone: String)

case class Project(id: Int, title: String, description: String, deadline: Date, customerId: Int)

// Bridge with Project ?
case class Task(id: Int, title: String, description: String, estimatedTime: Double, requiredTime: Double, projectId: Int)

case class Account(id: Int, username: String, email: String, hashedPassword: String, name: String)

case class History(id: Int, objectName: String, objectId: Int, action: Char, accountId: Int, timestamp: Timestamp)

// Bridge with History?
case class HistoryChange(id: Int, historyId: Int, fieldName: String, oldValue: String)
