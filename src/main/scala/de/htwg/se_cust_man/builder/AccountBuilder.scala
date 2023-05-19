package de.htwg.se_cust_man.builder

import de.htwg.se_cust_man.Account
import java.security.MessageDigest


class AccountBuilder extends Builder[Account]{
    var username: String = ""
    var email: String = ""
    var password: String = ""
    var name: String = ""

    def setUsername(username: String): AccountBuilder = {
        this.username = username
        this
    }
    def setEmail(email: String): AccountBuilder = {
        this.email = email
        this
    }
    def setPassword(clearPassword: String): AccountBuilder = {
        this.password = MessageDigest.getInstance("SHA-256").digest(password.getBytes("UTF-8")).map("%02x".format(_)).mkString
        this
    }
    def setName(name: String): AccountBuilder = {
        this.name = name
        this
    }
    def build = Account(-1, username, email, password, name)
    def reset = new AccountBuilder
}
