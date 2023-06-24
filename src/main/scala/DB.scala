package de.htwg.scm;

import io.github.cdimascio.dotenv.Dotenv

import java.sql.DriverManager
import java.sql.Connection

object DB {
  def url: String = {
    val dotenv: Dotenv = Dotenv.load()

    val host = dotenv.get("DB_HOST")
    val port = dotenv.get("DB_PORT")
    val database = dotenv.get("POSTGRES_DB")
    val username = dotenv.get("POSTGRES_USER")
    val password = dotenv.get("POSTGRES_PASSWORD")
    s"jdbc:postgresql://$host:$port/$database?&user=$username&password=$password"
  }

  def connect: Connection = {
    DriverManager.getConnection(url)
  }
}