package de.htwg.se_cust_man.server

import java.net.*
import java.io.*
import java.util.concurrent.{BlockingQueue, LinkedBlockingQueue}

class ClientConnect(val clientSocket: Socket, val queue: BlockingQueue[String]) extends Thread {
  val out: PrintWriter = new PrintWriter(clientSocket.getOutputStream, true)
  val in: BufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream))

  override def run(): Unit = {
    while (clientSocket.isConnected) {
      val line = in.readLine()
      queue.put(line)
    }
  }

  def send(msg: String): Unit = {
    out.println(msg)
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
    connections.foreach(x => x.send(msg))
  }
  val hander = new HandleMessages(queue, sendAll)

  def loop(): Unit = {
    hander.start()
    while (!serverSocket.isClosed) {
      val client = serverSocket.accept()
      val t = new ClientConnect(client, queue)
      connections = connections :+ t
      t.start()
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