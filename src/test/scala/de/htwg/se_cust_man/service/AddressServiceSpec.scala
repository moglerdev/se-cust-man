package de.htwg.se_cust_man.service

import org.scalatest.*
import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec
import de.htwg.se_cust_man.builder.{AddressBuilder, CustomerBuilder, ProjectBuilder, TaskBuilder}
import java.util.Date
import de.htwg.se_cust_man.service.AddressService
import de.htwg.se_cust_man.Address

class AddressServiceSpec extends AnyWordSpec with Matchers {
  "Address Service Test" should {
    val test = AddressService.getInstance("test");
    "create a Address" in {
      val address = test.insertAddress(Address(1, "okay", "okay", "okay", "okay"))
      address.street must be("okay")
      address.zip must be("okay")
      address.city must be("okay")
      address.isoCode must be("okay")
    }
    "get a Address by id" in {
      val address = test.getAddressById(1)
      address.get.street must be("street")
      address.get.zip must be("zip")
      address.get.city must be("city")
      address.get.isoCode must be("isoCode")
    }
    "get all Addresss" in {
      val addresss = test.getAddresses
      addresss.size must be(2)
      addresss(0).street must be("street")
      addresss(0).zip must be("zip")
      addresss(0).city must be("city")
      addresss(0).isoCode must be("isoCode")
    }
  }
}
