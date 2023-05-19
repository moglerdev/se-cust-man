package de.htwg.se_cust_man.service

import org.scalatest.*
import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec
import de.htwg.se_cust_man.builder.{HistoryBuilder, ProjectBuilder, TaskBuilder}
import java.util.Date
import de.htwg.se_cust_man.service.HistoryService
import java.security.Timestamp
import de.htwg.se_cust_man.{History, HistoryChange}

class HistoryServiceSpec extends AnyWordSpec with Matchers {
  "History Service Test" should {
    val test = HistoryService.getInstance("test");
    "create a History" in {
      val history = test.insertHistory(History(1, "okay", 2, 'D', 1, new java.sql.Timestamp(0)), Vector(HistoryChange(1, 1, "okay", "okay")))
      history.objectName must be("okay")
      history.objectId must be(2)
      history.action must be('D')
      history.accountId must be(1)
      history.timestamp must be(new java.sql.Timestamp(0))
    }
    "get a History by id" in {
      val customer = test.getHistoryById(1)
      customer.get.objectName must be("Customer")
      customer.get.objectId must be(1)
      customer.get.action must be('U')
      customer.get.accountId must be(1)
      customer.get.timestamp must be(new java.sql.Timestamp(0))

    }
    "get all Historys" in {
      val customers = test.getHistorys
      customers.size must be(1)
      customers(0).objectName must be("Customer")
      customers(0).objectId must be(1)
      customers(0).action must be('U')
      customers(0).accountId must be(1)
      customers(0).timestamp must be(new java.sql.Timestamp(0))
  
    }
  }
}
