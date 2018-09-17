package com.purchases.backend.identity

import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority

class UserAuthentication(private val user: TokenUser) : Authentication {
    private var authenticated = true

    override fun getName(): String {
        return user.username
    }

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return user.authorities
    }

    override fun getCredentials(): Any {
        return user.password
    }

    override fun getDetails(): TokenUser {
        return user
    }

    override fun getPrincipal(): Any {
        return user.username
    }

    override fun isAuthenticated(): Boolean {
        return authenticated
    }

    override fun setAuthenticated(authenticated: Boolean) {
        this.authenticated = authenticated
    }
}
