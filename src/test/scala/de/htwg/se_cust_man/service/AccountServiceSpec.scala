package de.htwg.se_cust_man.service

import org.scalatest.*
import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec
import de.htwg.se_cust_man.builder.{AccountBuilder, AddressBuilder, CustomerBuilder, ProjectBuilder, TaskBuilder}
import java.util.Date
import de.htwg.se_cust_man.service.AccountService
import de.htwg.se_cust_man.Account

class AccountServiceSpec extends AnyWordSpec with Matchers {
  "Account Service Test" should {
    val test = AccountService.getInstance("test");
    "create a Account" in {
      val account = test.insertAccount(Account(1, "okay", "okay", "okay", "okay"))
      account.username must be("okay")
      account.email must be("okay")
      account.hashedPassword must be("okay")
      account.name must be("okay")
    }
    "get a Account by username" in {
      val account = test.getAccountByUsername("username")
      account.get.username must be("username")
      account.get.email must be("email")
      account.get.hashedPassword must be("hashedPassword")
      account.get.name must be("name")
    }
    "get a Account by id" in {
      val account = test.getAccountById(1)
      account.get.username must be("username")
      account.get.email must be("email")
      account.get.hashedPassword must be("hashedPassword")
      account.get.name must be("name")
    }
    "get all Accounts" in {
      val accounts = test.getAccounts
      accounts.size must be(2)
      accounts(0).username must be("username")
      accounts(0).email must be("email")
      accounts(0).hashedPassword must be("hashedPassword")
      accounts(0).name must be("name")
    }
  }
}
