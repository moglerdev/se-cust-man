package de.htwg.se_cust_man.handler

import de.htwg.se_cust_man.Setting
import de.htwg.se_cust_man.service.AccountService
import de.htwg.se_cust_man.Account


// ------------------------------------------------------------------------
// Chain of Responsibility [2/4]
// https://refactoring.guru/design-patterns/chain-of-responsibility

abstract class ChainRequest(val session: Option[Account]) {
}

trait ChainResponse {
    val status: Int
    val message: String
}

class NoHandlerFoundRes extends ChainResponse {
    val status = 404
    val message = "No handler found"
}

class ChainHandler {

    var next: Option[ChainHandler] = None

    def setNext(handler: ChainHandler): ChainHandler = {
        next = Some(handler)
        handler
    }

    def handle(request: ChainRequest): ChainResponse = {
        if (next.isDefined) {
            next.get.handle(request)
        } else {
            NoHandlerFoundRes()
        }
    }
}

object ChainHandler {
    def createChain: ChainHandler = {
        val authHandler = new AuthenticateHandler()
        val checkAuthHandler = new CheckAuthHandler()
        val customerHandler = new CustomerHandler()
        val addressHandler = new AddressHandler()
        // val addressHandler = new AddressHandler()
        authHandler
            .setNext(checkAuthHandler)
            .setNext(customerHandler)
            .setNext(addressHandler)
            // .setNext(accountHandler)
            // .setNext(addressHandler)
        authHandler
    }
}