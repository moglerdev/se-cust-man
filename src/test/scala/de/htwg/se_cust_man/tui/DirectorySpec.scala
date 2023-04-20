package de.htwg.se_cust_man.tui

import org.scalatest.*
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should._

class DirectorySpec extends AnyFlatSpec with Matchers {

    "A Directory" should "have a name" in {
        val dir = new Directory("dir")
        dir.getName shouldEqual "dir"
    }

    it should "be a directory" in {
        val dir = new Directory("dir")
        dir.isDirectory shouldBe true
    }

    it should "have a path" in {
        val rootDir = new Directory("root")
        val dir = new Directory("dir", Some(rootDir))
        dir.getPath shouldEqual "root/dir"
    }

    it should "find a subdirectory by path" in {
        val rootDir = new Directory("root")
        val subDir = new Directory("subdir", Some(rootDir))
        val dir = new Directory("dir", Some(subDir))
        rootDir.findDir("subdir/dir") shouldEqual Some(dir)
    }

    it should "not find a non-existing subdirectory by path" in {
        val rootDir = new Directory("root")
        rootDir.findDir("non-existing-dir") shouldEqual None
    }

    it should "find a file by path" in {
        val rootDir = new Directory("root")
        val file = new File("file", Some(rootDir))
        rootDir.findFile("file") shouldEqual Some(file)
    }

    it should "not find a non-existing file by path" in {
        val rootDir = new Directory("root")
        rootDir.findFile("non-existing-file") shouldEqual None
    }

    it should "have children" in {
        val dir = new Directory("dir")
        val file = new File("file", Some(dir))
        dir.getFiles should contain (file)
    }

    it should "add and remove children" in {
        val dir = new Directory("dir")
        val file = new File("file", Some(dir))
        dir.getFiles should contain (file)
        dir.removeChild(file)
        dir.getFiles should not contain (file)
    }

    it should "execute a 'cd' command" in {
        val rootDir = new Directory("root")
        val dir1 = new Directory("dir1", Some(rootDir))
        val dir2 = new Directory("dir2", Some(rootDir))
        Directory.changeDir(
            Input("cd", Vector("dir2")), rootDir) shouldEqual Executed(Input("cd", Vector("dir2")),
            ExecutedResult.Open,
            Some(dir2),
            None)
    }

    it should "execute a 'ls' command" in {
        val rootDir = new Directory("root")
        val dir = new Directory("dir", Some(rootDir))
        val file = new File("file", Some(dir))
        Directory.listDir(Input("ls", Vector()),
            dir) shouldEqual Executed(Input("ls", Vector()), ExecutedResult.Success, Some(dir), Some(file.getName))
    }

    it should "execute a 'make customer' command" in {
        val rootDir = new Directory("root")
        val dir = new Directory("dir", Some(rootDir))
        Directory.makeCustomer(Input("make", Vector("customer", "customer1")), dir) shouldEqual Executed(
            Input("make", Vector("customer", "customer1")),
            ExecutedResult.Success, Some(dir),
            Some("Customer created"))
    }

    it should "not execute an unknown command" in {
        val rootDir = new Directory("root")
        val dir = new Directory("dir", Some(rootDir))
        dir.execute(Input("unknown", Vector())) shouldEqual None
    }
}