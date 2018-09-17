package com.purchases.backend.identity

import com.purchases.backend.model.User
import com.sun.org.apache.xml.internal.security.algorithms.SignatureAlgorithm
import io.jsonwebtoken.Jwts
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import java.util.*
import javax.servlet.http.HttpServletRequest

@Service
class TokenUtil {

    private val secret = "mrin"

    fun verifyToken(request: HttpServletRequest): Optional<Authentication> {
        val token = request.getHeader(AUTH_HEADER_NAME)

        if (token != null && !token.isEmpty()) {
            val user = parseUserFromToken(token.replace("Bearer", "").trim { it <= ' ' })
            if (user != null) {
                return Optional.of(UserAuthentication(user))
            }
        }
        return Optional.empty()

    }

    //Get User Info from the Token
    fun parseUserFromToken(token: String): TokenUser? {

        val claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()

        val user = User()
        user.name = claims.get("userId") as String
        user.setRole(Role.valueOf(claims.get("role") as String))
        return if (user.getUserId() != null && user.getRole() != null) {
            TokenUser(user)
        } else {
            null
        }
    }

    fun createTokenForUser(tokenUser: TokenUser): String {
        return createTokenForUser(tokenUser.getUser())
    }

    fun createTokenForUser(user: User): String {
        return Jwts.builder()
                .setExpiration(Date(System.currentTimeMillis() + VALIDITY_TIME_MS))
                .setSubject(user.getFullName())
                .claim("userId", user.getUserId())
                .claim("role", user.getRole().toString())
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact()
    }

    companion object {

        //private static final long VALIDITY_TIME_MS = 10 * 24 * 60 * 60 * 1000;// 10 days Validity
        private val VALIDITY_TIME_MS = (2 * 60 * 60 * 1000).toLong() // 2 hours  validity
        private val AUTH_HEADER_NAME = "Authorization"
    }

}