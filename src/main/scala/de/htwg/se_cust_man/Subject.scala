package de.htwg.se_cust_man

class Subject {
    var subscribers: Vector[Observer] = Vector()

    def subscribe(subscriber: Observer): Unit = subscribers = subscribers :+ subscriber

    def unsubscribe(subscriber: Observer): Unit = subscribers = subscribers.filter(a => a != subscriber)

    def notifyObservers() : Unit = subscribers.foreach(a => a.update())
}