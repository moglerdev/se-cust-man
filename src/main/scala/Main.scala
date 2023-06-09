package de.htwg.scm

import view.gui.GuiApp
import view.tui.TuiApp

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
