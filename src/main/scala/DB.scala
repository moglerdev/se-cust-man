package de.htwg.scm;

import java.sql.DriverManager
import java.sql.Connection

object DB {
  val driver = "org.postgresql.Driver"
  val url = "jdbc:postgresql://192.168.178.72/postgres"
  val username = "postgres"
  val password = "UQ0KLI7DrTDSFsNQ"

  def connect: Connection = {
    DriverManager.getConnection(s"$url?user=$username&password=$password");
  }
}