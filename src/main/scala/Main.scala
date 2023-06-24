package de.htwg.scm

import de.htwg.scm.gui.GuiApp
import de.htwg.scm.tui.TuiApp

import java.io.File



object Main {
  def main(args: Array[String]): Unit = {
    // args host begins with --dbhost 
    if (args.contains("--dbhost")) {
      val host = args(args.indexOf("--dbhost") + 1)
      DB.host = host
    }

    println("Hello, world!")
    println(s"DB url: ${DB.url}")

    if (args.contains("--gui")) {
      val gui = new GuiApp
      gui.main(Array.empty)
    } else {
      TuiApp.start()
    }
  }
}
