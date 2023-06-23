package de.htwg.scm
package controller

abstract class Publisher {
  private var subscribers: Set[Subscriber] = Set()

  def subscribe(s: Subscriber): Unit = subscribers += s

  def unsubscribe(s: Subscriber): Unit = subscribers -= s

  def notifySubscribers(): Unit = subscribers.foreach(_.update())
}

trait Subscriber {
  def update(): Unit
}
