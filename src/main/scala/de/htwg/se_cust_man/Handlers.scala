package de.htwg.se_cust_man

import scala.util.{Failure, Success, Try}


abstract class Handler {
  var next : Option[Handler] = None

  def setNext(next: Handler) : Handler = {
    this.next = Some(next)
    next
  }

  def handle(request: String): Try[Boolean] = {
    if (next.isDefined) {
      next.get.handle(request)
    } else {
      Failure(new Exception("No Handler found for request: " + request))
    }
  }
}

class CustomerCommandHandler(key: String, call: (customer: Customer) => Unit) extends Handler {
  override def handle(request: String): Try[Boolean] = {
    if (request.trim().startsWith(key)) {
      val args = request.trim().split(" ")
      if (args.length != 5) return Failure(new Exception("Invalid number of arguments: " + args.length + " expected 5"))
      call(Customer(args(1), args(2), args(3), args(4)))
      Success(true)
    } else {
      super.handle(request)
    }
  }
}

class HistoryHandler(key: String, call: (name: String) => Unit) extends Handler {
  override def handle(request: String): Try[Boolean] = {
    if (request.trim().startsWith(key)) {
      if (request.trim().split(" ").length != 2) return Failure(new Exception("Invalid number of arguments: " + request.trim().split(" ").length + " expected 2"))
      call(request.trim().split(" ")(1))
      Success(true)
    } else {
      super.handle(request)
    }
  }
}
class PrintCustomerHandler(key: String, call: (name: String) => Unit) extends Handler {
  override def handle(request: String): Try[Boolean] = {
    if (request.trim().startsWith(key)) {
      if (request.trim().split(" ").length != 2)
        return Failure(new Exception("Invalid number of arguments: " + request.trim().split(" ").length + " expected 2"))
      call(request.trim().split(" ")(1))
      Success(true)
    } else {
      super.handle(request)
    }
  }
}

class ExitHandler(key: String) extends Handler {
  override def handle(request: String): Try[Boolean] = {
    if (request.trim().startsWith(key)) {
      System.exit(0)
      Try(true)
    } else {
      super.handle(request)
    }
  }
}

class UndoHandler(key: String, call: () => Unit) extends Handler {
  override def handle(request: String): Try[Boolean] = {
    if (request.trim().startsWith(key)) {
      call()
      Success(true)
    } else {
      super.handle(request)
    }
  }
}

class RedoHandler(key: String, call: () => Unit) extends Handler {
  override def handle(request: String): Try[Boolean] = {
    if (request.trim().startsWith(key)) {
      call()
      Success(true)
    } else {
      super.handle(request)
    }
  }
}
