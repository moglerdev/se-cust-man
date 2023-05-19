package de.htwg.se_cust_man.builder

import java.sql.Timestamp
import java.security.MessageDigest
import java.util.Date
import de.htwg.se_cust_man.Project
import de.htwg.se_cust_man.Customer
import de.htwg.se_cust_man.Task
import de.htwg.se_cust_man.Account
import de.htwg.se_cust_man.History
import de.htwg.se_cust_man.HistoryChange
import de.htwg.se_cust_man.Address

// ------------------------------------------------------------------------
// Builder [1/4]
// https://refactoring.guru/design-patterns/builder

trait Builder[Model] {
    def build: Model
    def reset: Builder[Model]
}

