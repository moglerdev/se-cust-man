package de.htwg.se_cust_man.cli

import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec

class CliSpec extends AnyWordSpec with Matchers{
  "ok" should {
    "sockets" in {
      Cli.startSocket() must be(true)
      Cli.subscribe((msg: String) => {}) must be(true)
      Cli.stopSocket() must be(true)
      Cli.send("test") must be(true)
    }
  }
}
