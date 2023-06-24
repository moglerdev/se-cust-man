package de.htwg.scm;

import java.sql.DriverManager
import java.sql.Connection

object DB {
  val driver = "org.postgresql.Driver"
  var host = "db"
  var db = "db"
  def url = s"jdbc:postgresql://${host}/${db}"
  val username = "scm"
  val password = "scm"

  def connect: Connection = {
    DriverManager.getConnection(s"$url?user=$username&password=$password");
  }
}