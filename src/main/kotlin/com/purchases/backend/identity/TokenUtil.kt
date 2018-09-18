package com.purchases.backend.identity

import com.purchases.backend.model.user.Role
import com.purchases.backend.model.user.User
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
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

        val user = User(claims.get("userId") as String, "", Role.valueOf(claims.get("role") as String))
        return TokenUser(user)
    }

    fun createTokenForUser(tokenUser: TokenUser): String {
        return createTokenForUser(tokenUser.getUser())
    }

    fun createTokenForUser(user: User): String {
        return Jwts.builder()
                .setExpiration(Date(System.currentTimeMillis() + VALIDITY_TIME_MS))
                .setSubject(user.name)
                .claim("userId", user.name)
                .claim("role", user.role.toString())
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact()
    }

    companion object {

        //private static final long VALIDITY_TIME_MS = 10 * 24 * 60 * 60 * 1000;// 10 days Validity
        private val VALIDITY_TIME_MS = (2 * 60 * 60 * 1000).toLong() // 2 hours  validity
        private val AUTH_HEADER_NAME = "Authorization"
    }

}