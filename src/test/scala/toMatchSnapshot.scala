package de.htwg.scm

import java.io.{File, PrintWriter}
import org.scalatest.matchers.{BeMatcher, MatchResult}

import scala.io.{BufferedSource, Source}

def toMatchSnapshot(expectedFile: String): BeMatcher[String] = new BeMatcher[String] {
  def apply(actual: String): MatchResult = {
    val file = new File(expectedFile)
    val expected: String =
      if (file.exists()) {
        val source: BufferedSource = Source.fromFile(file)
        try {
          source.getLines().mkString("\n")
        } finally {
          source.close()
        }
      } else {
        val writer = new PrintWriter(file)
        writer.write(actual)
        writer.close()
        actual
      }
    val matchResult = actual == expected
    MatchResult(
      matchResult,
      s"$actual did not match the snapshot in $expectedFile",
      s"$actual matched the snapshot in $expectedFile"
    )
  }
}
