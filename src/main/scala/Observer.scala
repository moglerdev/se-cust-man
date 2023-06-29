package de.htwg.scm

trait IObserver {
  def published(): Unit
}


trait IPublisher {
  def observe(s: IObserver): Int
  def unobserve(s: IObserver): Int
  def publish(): Int
}

abstract class Publisher extends IPublisher {
  private var observer: Set[IObserver] = Set()

  def observe(s: IObserver): Int = {
    observer += s
    observer.count(_ => true)
  }

  def unobserve(s: IObserver): Int = {
    observer -= s
    observer.count(_ => true)
  }

  def publish(): Int = {
    observer.foreach(_.published())
    observer.count(_ => true)
  }
}
