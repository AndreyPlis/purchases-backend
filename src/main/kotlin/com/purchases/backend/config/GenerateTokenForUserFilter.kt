package com.purchases.backend.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.purchases.backend.identity.TokenUser
import com.purchases.backend.identity.TokenUtil
import com.purchases.backend.model.response.ResponseStatusEnum
import com.purchases.backend.model.session.SessionItem
import com.purchases.backend.model.session.SessionResponse
import org.apache.commons.io.IOUtils
import org.json.JSONException
import org.json.JSONObject
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class GenerateTokenForUserFilter(urlMapping: String, authenticationManager: AuthenticationManager, private val tokenUtil: TokenUtil) : AbstractAuthenticationProcessingFilter(AntPathRequestMatcher(urlMapping)) {

    init {
        setAuthenticationManager(authenticationManager)
    }

    @Throws(AuthenticationException::class, IOException::class, ServletException::class, JSONException::class)
    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        try {
            val jsonString = IOUtils.toString(request.inputStream, "UTF-8")
            /* using org.json */
            val userJSON = JSONObject(jsonString)
            val username = userJSON.getString("username")
            val password = userJSON.getString("password")
            val browser = if (request.getHeader("User-Agent") != null) request.getHeader("User-Agent") else ""
            val ip = request.remoteAddr

            //final UsernamePasswordAuthenticationToken loginToken = new UsernamePasswordAuthenticationToken("demo", "demo");
            val authToken = UsernamePasswordAuthenticationToken(username, password)
            return authenticationManager.authenticate(authToken) // This will take to successfulAuthentication or faliureAuthentication function
        } catch (e: JSONException) {
            throw AuthenticationServiceException(e.message)
        } catch (e: AuthenticationException) {
            throw AuthenticationServiceException(e.message)
        }

    }

    @Throws(IOException::class, ServletException::class)
    override fun successfulAuthentication(req: HttpServletRequest, res: HttpServletResponse, chain: FilterChain?, authToken: Authentication) {
        SecurityContextHolder.getContext().authentication = authToken

        val tokenUser = authToken.principal as TokenUser
        val respItem = SessionItem()
        val resp = SessionResponse(respItem)
        val ow = ObjectMapper().writer().withDefaultPrettyPrinter()
        val tokenString = this.tokenUtil.createTokenForUser(tokenUser)

        respItem.name = tokenUser.getUser().name
        respItem.token = tokenString

        resp.operationStatus = ResponseStatusEnum.SUCCESS
        resp.operationMessage = "Login Success"
        val jsonRespString = ow.writeValueAsString(resp)

        res.status = HttpServletResponse.SC_OK
        res.writer.write(jsonRespString)
        //res.getWriter().write(jsonResp.toString());
        res.writer.flush()
        res.writer.close()

        // DONT call supper as it contine the filter chain super.successfulAuthentication(req, res, chain, authResult);
    }
}
