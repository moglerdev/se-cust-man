package de.htwg.scm

import gui.GuiApp
import tui.TuiApp

import java.io.File



object Main {
  def main(args: Array[String]): Unit = {
    println(s"DB url: ${DB.url}")

    if (args.contains("--gui")) {
      val gui = new GuiApp
      gui.main(Array.empty)
    } else {
      TuiApp.start()
    }
  }
}
