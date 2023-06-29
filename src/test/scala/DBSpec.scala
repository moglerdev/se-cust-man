package de.htwg.scm

import io.github.cdimascio.dotenv.Dotenv
import org.mockito.Mockito.when
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.mockito.MockitoSugar

import java.sql.{Connection, DriverManager}

class DBSpec extends AnyWordSpec with Matchers with MockitoSugar{
  "DB" should {
    "return the correct URL" in {
      val dotenv = Dotenv.load()
      val host = dotenv.get("DB_HOST")
      val port = dotenv.get("DB_PORT")
      val db = dotenv.get("POSTGRES_DB")
      val user = dotenv.get("POSTGRES_USER")
      val pwd = dotenv.get("POSTGRES_PASSWORD")

      val expectedUrl = s"jdbc:postgresql://$host:$port/$db?&user=$user&password=$pwd"

      val url = DB.url
      url shouldEqual expectedUrl
    }
  }
}
