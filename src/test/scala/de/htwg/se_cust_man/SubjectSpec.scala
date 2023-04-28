package de.htwg.se_cust_man

import org.scalatest.*
import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec


class SubjectSpec extends AnyWordSpec with Matchers {
  "subject subscribe" should {
    val subject = new Subject
    var updated = false
    val observer: Observer = () => updated = true
    "notify observers" in {
      subject.subscribe(observer)
      updated = false
      subject.notifyObservers()
      updated must be(true)
    }
    "unsubscribe" in {
      subject.unsubscribe(observer)
      updated = false
      subject.notifyObservers()
      updated must be(false)
    }
  }
}
