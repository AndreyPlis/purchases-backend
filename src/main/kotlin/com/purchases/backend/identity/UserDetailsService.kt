package com.purchases.backend.identity

import com.purchases.backend.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AccountStatusUserDetailsChecker
import org.springframework.security.authentication.DisabledException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsService : org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private val userRepo: UserRepository? = null
    private val detailsChecker = AccountStatusUserDetailsChecker()

    @Throws(UsernameNotFoundException::class, DisabledException::class)
    override fun loadUserByUsername(username: String): TokenUser {

        val user = userRepo!!.findOneByUserId(username).orElseThrow({ UsernameNotFoundException("User not found") })
        val currentUser: TokenUser
        currentUser = TokenUser(user)
        detailsChecker.check(currentUser)
        return currentUser
    }
}
