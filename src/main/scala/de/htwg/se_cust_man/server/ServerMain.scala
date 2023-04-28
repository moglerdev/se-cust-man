package de.htwg.se_cust_man.server

import java.net.*
import java.io.*
import java.util.concurrent.{BlockingQueue, LinkedBlockingQueue}

class ClientConnect(val id: Int, val clientSocket: Socket, val queue: BlockingQueue[String], val onError: (Int, Exception) => Unit, val onClose: (Int) => Unit) extends Thread {
  val out: PrintWriter = new PrintWriter(clientSocket.getOutputStream, true)
  val in: BufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream))

  var isRunning = false

  override def run(): Unit = {
    isRunning = true
    try {
      while (isRunning) {
        val line = in.readLine()
        if (line == null) {
          isRunning = false
        } else {
          queue.put(id + "__" + line)
        }
      }
    } catch {
      case e: Exception => onError(id, e)
    } finally {
      this.close()
      onClose(id)
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
  var isRunning = false

  override def run(): Unit = {
    isRunning = true
    while (isRunning) {
      val str = queue.take()
      println(str)
      sendAll(str)
    }
  }
}


class SmallServer(port: Int) {
  private val queue = new LinkedBlockingQueue[String]

  private var serverSocket: ServerSocket = new ServerSocket(port)
  private var connections: Vector[ClientConnect] = Vector.empty
  println(s"Server is now running on Port $port")

  private def sendAll(msg: String) : Unit = {
    val o = msg.split("__")
    val id = o(0).toInt
    connections.filter( x => x.id != id).foreach(x => x.send(o(1)))
  }
  private val hander = new HandleMessages(queue, sendAll)

  private def handleError(id: Int, e: Exception): Unit = {
    println("Error: " + e.getMessage)
    connections = connections.filter(x => x.id != id)
  }

  private def handleClose(id: Int) : Unit = {
    println(s"Client with the ID $id has disconnected")
    connections = connections.filter(x => x.id != id)
  }

  def loop(): Unit = {
    hander.start()
    var counter = 0;
    while (!serverSocket.isClosed) {
      println("Waiting for new Client")
      val client = serverSocket.accept()
      val t = new ClientConnect(counter, client, queue, handleError, handleClose)
      connections = connections :+ t
      t.start()
      println(s"New Client connected with the ID $counter")
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
  println("Server is now closed")
}