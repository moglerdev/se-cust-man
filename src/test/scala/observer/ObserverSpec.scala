package observer

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ObserverSpec extends AnyFlatSpec with Matchers {

  class TestObserver extends IObserver {
    var publishedCalled: Boolean = false

    def published(): Unit = {
      publishedCalled = true
    }
  }

  class TestPublisher extends Publisher {
    def testPublish(): Int = publish()
  }

  "An Observer" should "be notified when publish is called on the Publisher" in {
    val observer1 = new TestObserver
    val observer2 = new TestObserver
    val publisher = new TestPublisher

    publisher.observe(observer1)
    publisher.observe(observer2)

    observer1.publishedCalled should be(false)
    observer2.publishedCalled should be(false)

    publisher.testPublish()

    observer1.publishedCalled should be(true)
    observer2.publishedCalled should be(true)
  }

  it should "stop receiving notifications after being unobserved" in {
    val observer = new TestObserver
    val publisher = new TestPublisher

    publisher.observe(observer)

    observer.publishedCalled should be(false)

    publisher.testPublish()

    observer.publishedCalled should be(true)

    observer.publishedCalled = false
    publisher.unobserve(observer)

    publisher.testPublish()

    observer.publishedCalled should be(false)
  }
}
