package de.htwg.scm
package store

import model.{Customer, CustomerBuilder}

import java.sql.{Connection, PreparedStatement}
import com.google.inject.{Guice, Inject, Injector}
import net.codingwell.scalaguice.InjectorExtensions._

class DBCustomerStore @Inject()(connection: Connection) extends ICustomerStore {
  def disconnect(): Unit = {
    if (connection != null) {
      connection.close()
      println("Disconnected from the database.")
    }
  }

  override def create(model: Customer): Int = {
    val statement: PreparedStatement = connection.prepareStatement("INSERT INTO customer (name, email, address, phone) VALUES (?, ?, ?, ?)")
    statement.setString(1, model.name)
    statement.setString(2, model.email)
    statement.setString(3, model.address)
    statement.setString(4, model.phone)
    statement.executeUpdate()
  }

  private def createCustomer(resultSet: java.sql.ResultSet): Customer = {
    val builder = new CustomerBuilder()
    builder.setId(resultSet.getInt("id"))
    builder.setName(resultSet.getString("name"))
    builder.setEmail(resultSet.getString("email"))
    builder.setPhoneNumber(resultSet.getString("phone"))
    builder.setAddress(resultSet.getString("address"))
    builder.build()
  }

  override def read(id: Int): Option[Customer] = {
    val statement: PreparedStatement = connection.prepareStatement("SELECT * FROM customer WHERE id = ?")
    statement.setInt(1, id)
    val resultSet = statement.executeQuery()
    if (resultSet.next()) {
      Some(createCustomer(resultSet))
    } else {
      None
    }
  }

  override def update(id: Int, model: Customer): Int = {
    val statement: PreparedStatement = connection.prepareStatement("UPDATE customer SET name = ?, email = ?, address = ?, phone = ? WHERE id = ?")
    statement.setString(1, model.name)
    statement.setString(2, model.email)
    statement.setString(3, model.address)
    statement.setString(6, model.phone)
    statement.setInt(7, model.id)
    statement.executeUpdate()
  }

  override def delete(id: Int): Int = {
    val statement: PreparedStatement = connection.prepareStatement("UPDATE customer SET _deleted = 1 WHERE id = ?")
    statement.setInt(1, id)
    statement.executeUpdate()
  }

  override def delete(model: Customer): Int = delete(model.id)
  
  override def getAll: List[Customer] = {
    val statement: PreparedStatement = connection.prepareStatement("SELECT * FROM customer where _deleted = 0")
    val resultSet = statement.executeQuery()
    var customers: List[Customer] = List.empty
    while (resultSet.next()) {
      val customer = createCustomer(resultSet)
      customers = customers :+ customer
    }
    customers
  }

  override def getLastId: Int = {
    val statement: PreparedStatement = connection.prepareStatement("select nextval('customer_id_seq')")
    val resultSet = statement.executeQuery()
    if (resultSet.next()) {
      resultSet.getInt("nextval") - 1
    } else {
      -1
    }

  }
}
