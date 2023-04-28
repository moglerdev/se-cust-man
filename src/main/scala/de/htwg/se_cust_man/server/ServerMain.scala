package de.htwg.se_cust_man.server

import java.net.*
import java.io.*
import java.util.concurrent.{BlockingQueue, LinkedBlockingQueue}

class ClientConnect(val id: Int, val clientSocket: Socket, val queue: BlockingQueue[String], val onError: (Int, Exception) => Unit) extends Thread {
  val out: PrintWriter = new PrintWriter(clientSocket.getOutputStream, true)
  val in: BufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream))

  override def run(): Unit = {
    try {
      while (clientSocket.isConnected) {
        val line = in.readLine()
        queue.put(id + "__" + line)
      }
    } catch {
      case e: Exception => onError(id, e)
    }
  }

  def send(msg: String): Unit = {
    out.println(msg)
    out.flush()
  }

  def close(): Unit = {
    in.close()
    out.close()
    clientSocket.close()
  }
}

class HandleMessages(val queue: BlockingQueue[String], sendAll: (msg: String) => Unit) extends Thread {
  override def run(): Unit = {
    while (true) {
      val str = queue.take()
      println(str)
      sendAll(str)
    }
  }
}


class SmallServer(port: Int) {
  val queue = new LinkedBlockingQueue[String]

  var serverSocket: ServerSocket = new ServerSocket(port)
  var connections: Vector[ClientConnect] = Vector.empty

  def sendAll(msg: String) : Unit = {
    val o = msg.split("__")
    val id = o(0).toInt
    connections.filter( x => x.id != id).foreach(x => x.send(o(1)))
  }
  val hander = new HandleMessages(queue, sendAll)

  def handleError(id: Int, e: Exception): Unit = {
    println("Error: " + e.getMessage)
    connections = connections.filter(x => x.id != id)
  }

  def loop(): Unit = {
    hander.start()
    var counter = 0;
    while (!serverSocket.isClosed) {
      val client = serverSocket.accept()
      val t = new ClientConnect(counter, client, queue, handleError)
      connections = connections :+ t
      t.start()
      counter+= 1
    }
  }

  def stop(): Unit = {
    connections.foreach(x => x.close())
    serverSocket.close()
  }
}

@main
def runServer(): Unit = {
  val sms = new SmallServer(25565)
  sms.loop()
}