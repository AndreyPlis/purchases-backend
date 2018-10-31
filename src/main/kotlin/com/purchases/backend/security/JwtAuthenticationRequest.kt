package com.purchases.backend.security

import java.io.Serializable

data class JwtAuthenticationRequest(var username: String, var password: String) : Serializable {

    companion object {
        private const val serialVersionUID = -8445943548965154778L
    }
}
