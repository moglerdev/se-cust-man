package de.htwg.se_cust_man.cli

import de.htwg.se_cust_man.handler.{ChainHandler, CustomerHandler, AddressHandler}
import de.htwg.se_cust_man.Customer
import de.htwg.se_cust_man.Address
import de.htwg.se_cust_man.Address
import de.htwg.se_cust_man.Account
import de.htwg.se_cust_man.service.AddressService
import de.htwg.se_cust_man.Observer

class CliView extends Observer {

    override def onNotify(): Unit = {
        println("Notified")
    }
    
}

object CliApp {
    @main
    def runCli(): Unit = {
        val chain = ChainHandler.createChain

        val session = Account(0, "Test", "Test", "Test", "Test")
        val adrId = 1

        val customer = Customer(0, "Test", adrId, "Test", "Test")
        val cusReq = CustomerHandler.createInsertRequest(Some(session), customer)
        val res = chain.handle(cusReq)
        if (res.status != 200) {
            println(res.message)
        } else {
            println(res.message)
        }
    }
}