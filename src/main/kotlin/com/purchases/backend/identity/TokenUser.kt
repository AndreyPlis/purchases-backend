package com.purchases.backend.identity

import com.purchases.backend.model.user.User
import org.springframework.security.core.authority.AuthorityUtils

class TokenUser
(private val user: User) : org.springframework.security.core.userdetails.User(user.name, user.password, AuthorityUtils.createAuthorityList(user.role.toString())) {


    fun getUser(): User {
        return user
    }

    fun getRole(): String {
        return user.role.toString();
    }
}