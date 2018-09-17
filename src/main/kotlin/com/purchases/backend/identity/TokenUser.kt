package com.purchases.backend.identity

import com.purchases.backend.model.User
import org.springframework.security.core.authority.AuthorityUtils

class TokenUser//super(user.getUserName(), user.getPassword(), true, true, true, true,  AuthorityUtils.createAuthorityList(user.getRole().toString()));
(private val user: User) : org.springframework.security.core.userdetails.User(user.name, user.password, AuthorityUtils.createAuthorityList("ADMIN")) {


    fun getUser(): User {
        return user
    }

    fun getRole(): String {
        return "ADMIN";
    }
}