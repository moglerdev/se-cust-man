package de.htwg.scm
package observer

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class PublisherSpec extends AnyWordSpec with Matchers {

  class TestObserver extends IObserver {
    var isPublished: Boolean = false

    override def published(): Unit = {
      isPublished = true
    }
  }

  "Publisher" should {

    "notify observers when published" in {
      // Create a publisher
      val publisher = new Publisher {}

      // Create test observers
      val observer1 = new TestObserver()
      val observer2 = new TestObserver()
      val observer3 = new TestObserver()

      // Observe the publisher
      publisher.observe(observer1)
      publisher.observe(observer2)
      publisher.observe(observer3)

      // Publish an event
      publisher.publish()

      // Verify that all observers were notified
      observer1.isPublished should be(true)
      observer2.isPublished should be(true)
      observer3.isPublished should be(true)
    }

    "not notify unobserved observers when published" in {
      // Create a publisher
      val publisher = new Publisher {}

      // Create test observers
      val observer1 = new TestObserver()
      val observer2 = new TestObserver()
      val observer3 = new TestObserver()

      // Observe the publisher
      publisher.observe(observer1)
      publisher.observe(observer2)

      // Unobserve observer2
      publisher.unobserve(observer2)

      // Publish an event
      publisher.publish()

      // Verify that observer1 was notified and observer2 and observer3 were not
      observer1.isPublished should be(true)
      observer2.isPublished should be(false)
      observer3.isPublished should be(false)
    }

    "return the correct observer count" in {
      // Create a publisher
      val publisher = new Publisher {}

      // Create test observers
      val observer1 = new TestObserver()
      val observer2 = new TestObserver()
      val observer3 = new TestObserver()

      // Observe the publisher
      publisher.observe(observer1)

      // Verify the observer count
      publisher.observe(observer2) should be(2)
      publisher.observe(observer3) should be(3)

      // Unobserve an observer
      publisher.unobserve(observer2)

      // Verify the updated observer count
      publisher.observe(observer2) should be(3)
    }
  }
}
