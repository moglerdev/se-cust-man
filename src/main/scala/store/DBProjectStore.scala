package de.htwg.scm
package store

import de.htwg.scm.model.Project

import java.sql.{Connection, PreparedStatement}
import com.google.inject.{Guice, Inject, Injector}
import net.codingwell.scalaguice.InjectorExtensions._


class DBProjectStore(connection: Connection) extends IProjectStore {

  def disconnect(): Unit = {
    if (connection != null) {
      connection.close()
      println("Disconnected from the database.")
    }
  }

  override def create(model: Project): Int = {
    val statement: PreparedStatement = connection.prepareStatement("INSERT INTO project (customer_id, title, description) VALUES (?, ?, ?)")
    statement.setInt(1, model.customer_id)
    statement.setString(2, model.title)
    statement.setString(3, model.description)
    statement.executeUpdate()
  }

  private def createProject(resultSet: java.sql.ResultSet): Project = {
    Project(
      resultSet.getInt("id"),
      resultSet.getInt("customer_id"),
      resultSet.getString("title"),
      resultSet.getString("description"))
  }

  override def read(id: Int): Option[Project] = {
    val statement: PreparedStatement = connection.prepareStatement("SELECT * FROM project WHERE id = ?")
    statement.setInt(1, id)
    val resultSet = statement.executeQuery()
    if (resultSet.next()) {
      Some(createProject(resultSet))
    } else {
      None
    }
  }

  override def update(id: Int, model: Project): Int = {
    val statement: PreparedStatement = connection.prepareStatement("UPDATE project SET title = ?, description = ? WHERE id = ?")
    statement.setString(1, model.title)
    statement.setString(2, model.description)
    statement.setInt(3, model.id)
    statement.executeUpdate()
  }

  override def delete(id: Int): Int = {
    val statement: PreparedStatement = connection.prepareStatement("UPDATE project SET _deleted = 1 WHERE id = ?")
    statement.setInt(1, id)
    statement.executeUpdate()
  }

  override def delete(model: Project): Int = delete(model.id)

  override def getAll: List[Project] = {
    val statement: PreparedStatement = connection.prepareStatement("SELECT * FROM project where _deleted = 0")
    val resultSet = statement.executeQuery()
    var projects: List[Project] = List.empty
    while (resultSet.next()) {
      projects = projects :+ createProject(resultSet)
    }
    projects
  }

  override def getLastId: Int = {
    val statement: PreparedStatement = connection.prepareStatement("select nextval('project_id_seq')")
    val resultSet = statement.executeQuery()
    if (resultSet.next()) {
      resultSet.getInt("nextval") - 1
    } else {
      -1
    }

  }
}
