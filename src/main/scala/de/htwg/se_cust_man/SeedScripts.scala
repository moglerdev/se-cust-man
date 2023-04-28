package de.htwg.se_cust_man

import java.sql.{Connection, DriverManager, ResultSet, SQLException, Statement}

class SeedScripts {
  var connection: Connection = null
  try {
    // create a database connection
    connection = DriverManager.getConnection("jdbc:sqlite:bin/scm.sqlite.db")
  } catch {
    case e: SQLException => println(e.getMessage)
    case e: Exception => println(e.getMessage)
  }

  def createCustomerTable(): Unit = {
    val statement = connection.createStatement
    statement.executeUpdate("create table if exists customer("+
      "id      integer constraint customer_pk primary key autoincrement," +
      "name    varchar(255) not null," +
      "address varchar(255) not null," +
      "phone   varchar(32)," +
      "email   varchar(255));")

  }
}
