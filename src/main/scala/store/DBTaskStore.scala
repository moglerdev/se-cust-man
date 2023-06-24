package de.htwg.scm
package store

import java.sql.{Connection, PreparedStatement}
import model.Task

class DBTaskStore extends ITaskStore {
  private val connection: Connection = DB.connect

  def disconnect(): Unit = {
    if (connection != null) {
      connection.close()
      println("Disconnected from the database.")
    }
  }

  override def create(model: Task): Int = {
    val statement: PreparedStatement = connection.prepareStatement("INSERT INTO task (project_id, title, description) VALUES (?, ?, ?)")
    statement.setInt(1, model.project_id)
    statement.setString(2, model.title)
    statement.setString(3, model.description)
    statement.executeUpdate()
  }

  private def createProject(resultSet: java.sql.ResultSet): Task = {
    Task(
      resultSet.getInt("id"),
      resultSet.getInt("project_id"),
      resultSet.getString("title"),
      resultSet.getString("description"))
  }

  override def read(id: Int): Option[Task] = {
    val statement: PreparedStatement = connection.prepareStatement("SELECT * FROM task WHERE id = ?")
    statement.setInt(1, id)
    val resultSet = statement.executeQuery()
    if (resultSet.next()) {
      Some(createProject(resultSet))
    } else {
      None
    }
  }

  override def update(id: Int, model: Task): Int = {
    val statement: PreparedStatement = connection.prepareStatement("UPDATE task SET title = ?, description = ? WHERE id = ?")
    statement.setString(1, model.title)
    statement.setString(2, model.description)
    statement.setInt(3, model.id)
    statement.executeUpdate()
  }

  override def delete(id: Int): Int = {
    val statement: PreparedStatement = connection.prepareStatement("UPDATE task SET _deleted = 1 WHERE id = ?")
    statement.setInt(1, id)
    statement.executeUpdate()
  }

  override def delete(model: Task): Int = delete(model.id)

  override def getAll: List[Task] = {
    val statement: PreparedStatement = connection.prepareStatement("SELECT * FROM task")
    val resultSet = statement.executeQuery()
    var projects: List[Task] = List.empty
    while (resultSet.next()) {
      projects = projects :+ createProject(resultSet)
    }
    projects
  }

  override def getLastId: Int = {
    val statement: PreparedStatement = connection.prepareStatement("select nextval('task_id_seq')")
    val resultSet = statement.executeQuery()
    if (resultSet.next()) {
      resultSet.getInt("nextval") - 1
    } else {
      -1
    }

  }
}
