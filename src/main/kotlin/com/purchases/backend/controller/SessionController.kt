package com.purchases.backend.controller

import com.purchases.backend.model.response.ResponseStatusEnum
import com.purchases.backend.model.session.SessionItem
import com.purchases.backend.model.session.SessionResponse
import com.purchases.backend.model.user.Login
import com.purchases.backend.repository.UserRepository
import io.swagger.annotations.Api
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@RestController
@Api(tags = arrayOf("Authentication"))
class SessionController {

    @Autowired
    lateinit var userRepo: UserRepository

    @ApiResponses(value = *arrayOf(ApiResponse(code = 200, message = "Will return a security token, which must be passed in every request", response = SessionResponse::class)))
    @RequestMapping(value = "/session", method = arrayOf(RequestMethod.POST), consumes = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    @ResponseBody
    fun newSession(@RequestBody login: Login, request: HttpServletRequest, response: HttpServletResponse): SessionResponse {
        val user = userRepo.findOneByNameAndPassword(login.username, login.password).orElse(null)
        val sessionItem = SessionItem()
        val resp = SessionResponse(sessionItem)
        if (user != null) {
            sessionItem.token = "xxx.xxx.xxx"
            sessionItem.name = user.name

            resp.operationStatus = ResponseStatusEnum.SUCCESS
            resp.operationMessage = "Dummy Login Success"
        } else {
            resp.operationStatus = ResponseStatusEnum.ERROR
            resp.operationMessage = "Login Failed"
        }
        return resp
    }

}