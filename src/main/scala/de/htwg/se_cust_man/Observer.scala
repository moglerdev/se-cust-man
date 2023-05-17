package de.htwg.se_cust_man

// ------------------------------------------------------------------------
// Observer [0/4]
// https://refactoring.guru/design-patterns/observer

trait Observer {
    def onNotify(): Unit
}

class Subject {
    var subscribers: Vector[Observer] = Vector()

    def subscribe(subscriber: Observer): Unit = subscribers = subscribers :+ subscriber

    def unsubscribe(subscriber: Observer): Unit = subscribers = subscribers.filter(a => a != subscriber)

    def notifyObservers() : Unit = subscribers.foreach(a => a.onNotify())
}