package de.htwg.se_cust_man.service


import java.sql.Connection
import java.sql.Statement
import java.sql.ResultSet
import de.htwg.se_cust_man.{Account, DB}

// ------------------------------------------------------------------------
// Proxy [3/4]
// https://refactoring.guru/design-patterns/proxy


trait AccountService {
    def insertAccount(account: Account): Account
    def updateAccount(account: Account): Account
    def getAccounts: Vector[Account]
    def getAccountById(id: Int): Option[Account]
    def removeAccount(account: Account): Boolean
}

class AccountServiceSql extends AccountService {
    var conn : Connection = DB.connect
    def insertAccount(account: Account) = {
        if (conn.isClosed()) {
            conn = DB.connect
        }
        val st = conn.prepareStatement("INSERT INTO account(username, email, hashed_password, name) VALUES (?, ?, ?, ?)");
        st.setString(1, account.username);
        st.setString(2, account.email);
        st.setString(3, account.hashedPassword);
        st.setString(4, account.name);
        st.executeUpdate();
        st.close();
        account
    }

    def updateAccount(account: Account) = {
        if (conn.isClosed()) {
            conn = DB.connect
        }
        val st = conn.prepareStatement("UPDATE account SET username = ?, email = ?, hashed_password = ?, name = ? WHERE id = ?");
        st.setString(1, account.username);
        st.setString(2, account.email);
        st.setString(3, account.hashedPassword);
        st.setString(4, account.name);
        st.setInt(5, account.id);
        st.executeUpdate();
        st.close();
        account
    }

    def getAccounts: Vector[Account] = {
        if (conn.isClosed()) {
            conn = DB.connect
        }
        val st = conn.createStatement();
        val rs = st.executeQuery("SELECT * FROM account");
        var accounts = Vector[Account]()
        while (rs.next()) {
            accounts = accounts :+ Account(rs.getInt("id"), rs.getString("username"), rs.getString("email"), rs.getString("hashed_password"), rs.getString("name"))
        }
        rs.close();
        st.close();
        accounts
    }
    def getAccountById(id: Int): Option[Account] = {
        if (conn.isClosed()) {
            conn = DB.connect
        }
        val st = conn.prepareStatement("SELECT * FROM account WHERE id = ?");
        st.setInt(1, id);
        val rs = st.executeQuery();
        var account: Option[Account] = None
        if (rs.next()) {
            account = Some(Account(rs.getInt("id"), rs.getString("username"), rs.getString("email"), rs.getString("hashed_password"), rs.getString("name")))
        }
        rs.close();
        st.close();
        account
    }
    
    def removeAccount(account: Account): Boolean = {
        if (conn.isClosed()) {
            conn = DB.connect
        }
        val st = conn.prepareStatement("DELETE FROM account WHERE id = ?");
        st.setInt(1, account.id);
        val rs = st.executeUpdate();
        st.close();
        rs > 0
    }
}

class AccountServiceRest extends AccountService {
    def insertAccount(account: Account) = {
        account
    }
    def updateAccount(account: Account) = {
        account
    }
    def deleteAccount(account: Account) = {
        account
    }
    def getAccounts: Vector[Account] = {
        Vector()
    }
    def getAccountById(id: Int): Option[Account] = {
        None
    }
    def getAccountByName(name: String): Option[Account] = {
        None
    }
    def removeAccount(account: Account): Boolean = {
        false
    }
}
