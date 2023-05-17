package de.htwg.se_cust_man


// ------------------------------------------------------------------------
// Chain of Responsibility [2/4]
// https://refactoring.guru/design-patterns/chain-of-responsibility

case class HandlerRequest(account: Account, action: String, data: Any)

abstract class Handler {
    var next: Handler
    def setNext(handler: Handler): Handler = {
        handler.next = this
        handler
    }

    def handle(request: HandlerRequest): Boolean
}

// class AuthHandler extends Handler {

//     override def handle(request: HandlerRequest): Boolean = throw new Exception("Not implemented")
// }
