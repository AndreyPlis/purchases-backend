package com.purchases.backend.config

import com.purchases.backend.identity.TokenUtil
import io.jsonwebtoken.JwtException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.GenericFilterBean
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class VerifyTokenFilter
//private AuthenticationFailureHandler loginFailureHandler = new SimpleUrlAuthenticationFailureHandler();

(private val tokenUtil: TokenUtil) : GenericFilterBean() {

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(req: ServletRequest, res: ServletResponse, filterChain: FilterChain) {
        val request = req as HttpServletRequest
        val response = res as HttpServletResponse
        try {
            val authentication = tokenUtil.verifyToken(request)
            if (authentication.isPresent()) {
                SecurityContextHolder.getContext().authentication = authentication.get()
            } else {
                SecurityContextHolder.getContext().authentication = null
            }
            filterChain.doFilter(req, res)
        } catch (e: JwtException) {
            response.status = HttpServletResponse.SC_UNAUTHORIZED
        } finally {
            SecurityContextHolder.getContext().authentication = null
        }
    }

}