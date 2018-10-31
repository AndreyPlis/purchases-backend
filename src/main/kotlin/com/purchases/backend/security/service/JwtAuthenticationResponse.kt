package com.purchases.backend.security.service

import java.io.Serializable

class JwtAuthenticationResponse(val token: String) : Serializable {
    companion object {

        private const val serialVersionUID = 1250166508152483573L
    }
}
