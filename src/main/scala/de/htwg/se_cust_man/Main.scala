import com.googlecode.lanterna.input.KeyType
import com.googlecode.lanterna.{SGR, TextColor}
import com.googlecode.lanterna.terminal.DefaultTerminalFactory

@main
def main(): Unit = {
  val defaultTerminalFactory = new DefaultTerminalFactory()

  val terminal = defaultTerminalFactory.createTerminal()
  terminal.enableSGR(SGR.BOLD)
  terminal.enterPrivateMode()
  terminal.setCursorVisible(false)

  val textGraphics = terminal.newTextGraphics
  textGraphics.setForegroundColor(TextColor.ANSI.WHITE)
  textGraphics.setBackgroundColor(TextColor.ANSI.BLACK)
  textGraphics.putString(2, 1, "Lanterna Tutorial 2 - Press ESC to exit", SGR.BOLD)
  textGraphics.setForegroundColor(TextColor.ANSI.DEFAULT)
  textGraphics.setBackgroundColor(TextColor.ANSI.DEFAULT)
  textGraphics.putString(5, 3, "Terminal Size: ", SGR.BOLD)
  textGraphics.putString(5 + "Terminal Size: ".length, 3, terminal.getTerminalSize.toString)

  terminal.addResizeListener((_, newSize) => {
    textGraphics.setForegroundColor(TextColor.ANSI.DEFAULT)
    textGraphics.setBackgroundColor(TextColor.ANSI.DEFAULT)
    textGraphics.putString(5, 3, "Terminal Size: ", SGR.BOLD)
    textGraphics.putString(5 + "Terminal Size: ".length, 3, newSize.toString)
  })

  terminal.flush()
  terminal.clearScreen()
  terminal.exitPrivateMode()

  var keyStroke = terminal.readInput();
  while (keyStroke.getKeyType != KeyType.Escape) {
    textGraphics.drawLine(5, 4, terminal.getTerminalSize.getColumns - 1, 4, ' ')
    textGraphics.putString(5, 4, "Last Keystroke: ", SGR.BOLD)
    textGraphics.putString(5 + "Last Keystroke: ".length, 4, keyStroke.toString)
    terminal.flush
    keyStroke = terminal.readInput
  }
}