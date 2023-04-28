package de.htwg.se_cust_man.server

import de.htwg.se_cust_man.Subject

import java.net.*
import java.io.*
import java.util.concurrent.LinkedBlockingQueue

class MessageListener(in: BufferedReader, onMessage: (msg: String) => Unit) extends Thread {
  override def run(): Unit = {
    while (true) {
      val line = in.readLine()
      onMessage(line)
    }
  }
}

abstract class ServerApi {
  var subscribers: Vector[(String) => Unit] = Vector.empty

  def subscribe(onMessage: (String) => Unit): Unit = {
    subscribers = subscribers :+ onMessage
  }
}

class ClientHelper extends ServerApi {

  private var clientSocket: Option[Socket] = None
  private var out: Option[PrintWriter] = None
  private var in: Option[BufferedReader] = None

  private val msgs = new LinkedBlockingQueue[String]
  private var msgListener : Option[MessageListener] = None

  def handleMessage(msg: String): Unit = {
    this.subscribers.foreach(x => x(msg))
  }

  def startConnection(ip: String, port: Int): Unit = {
    clientSocket = Some(new Socket(ip, port))
    out = Some(new PrintWriter(clientSocket.get.getOutputStream, true))
    in = Some(new BufferedReader(new InputStreamReader(clientSocket.get.getInputStream)))
    msgListener = Some(new MessageListener(in.get, handleMessage));
  }

  def sendMessage(msg: String): Unit = {
    out.get.println(msg)
  }

  def stopConnection(): Unit = {
    in.get.close()
    out.get.close()
    clientSocket.get.close()
  }
}
