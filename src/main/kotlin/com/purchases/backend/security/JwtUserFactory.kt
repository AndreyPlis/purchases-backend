package com.purchases.backend.security

import com.purchases.backend.model.Authority
import com.purchases.backend.model.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority

class JwtUserFactory {

    companion object Factory {
        fun create(user: User): JwtUser {
            return JwtUser(
                    user.id,
                    user.username,
                    user.firstname,
                    user.lastname,
                    user.email,
                    user.password,
                    mapToGrantedAuthorities(user.authorities),
                    user.enabled,
                    user.lastPasswordResetDate
            )
        }
        private fun mapToGrantedAuthorities(authorities: List<Authority>): List<GrantedAuthority> {
            return authorities.map { authority -> SimpleGrantedAuthority(authority.name.name) }

        }
    }
}
