print("-- Welcome to SE Cust-Man -- " + "--" * 2)
print("|")
print("")



val chars = List("/", "-", "\\", "|")
(0 to 30).foreach { _ => chars.foreach { cc =>
        print(s"\u0008$cc")
        Thread.sleep(150)
    }
}