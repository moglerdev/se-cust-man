package de.htwg.se_cust_man.handler

import de.htwg.se_cust_man.Account
import de.htwg.se_cust_man.Setting
import de.htwg.se_cust_man.service.AccountService
import de.htwg.se_cust_man.handler.{ChainHandler, ChainRequest, ChainResponse}

class NotAuthenticatedRes(msg: String = "Not authenticated") extends ChainResponse {
    val status = 403
    val message = msg
}

class CheckAuthHandler extends ChainHandler {

    override def handle(request: ChainRequest): ChainResponse = {
        if (request.session.isDefined) {
            super.handle(request)
        } else {
            NotAuthenticatedRes()
        }
    }
}

class AuthenticateRequest(val username: String, val password: String) extends ChainRequest(None)

class AuthenticateResponse(val account: Account) extends ChainResponse {
    val status = 200
    val message = "Authenticated"
    val session: Account = account
}

class AuthenticateHandler extends ChainHandler {

    override def handle(request: ChainRequest): ChainResponse = {
        if (request.isInstanceOf[AuthenticateRequest]) {
            val accountService = AccountService.getInstance(Setting.serviceProvider)
            val authRequest = request.asInstanceOf[AuthenticateRequest]
            val account = accountService.getAccountByUsername(authRequest.username)
            if (account.isDefined && account.get.checkPassword(authRequest.password)) {
                AuthenticateResponse(account.get)
            } else {
                NotAuthenticatedRes("Wrong username or password")
            }

        } else {
            super.handle(request)
        }
    }
}

