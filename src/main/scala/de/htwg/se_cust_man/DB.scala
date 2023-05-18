package de.htwg.se_cust_man

import java.sql.DriverManager
import java.sql.Connection

object DB {
    val driver = "org.postgresql.Driver"
    val url = "jdbc:postgresql://localhost/postgres"
    val username = "okay"
    val password = "CHANGE_ME"

    def connect = {
        DriverManager.getConnection(s"$url?user=$username&password=$password");
    }
}