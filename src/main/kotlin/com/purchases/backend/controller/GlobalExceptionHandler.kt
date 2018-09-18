package com.purchases.backend.controller


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.web.servlet.error.ErrorAttributes
import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.ServletWebRequest
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@RestController
class GlobalExceptionHandler : ErrorController {


    @Autowired
    lateinit var errorAttributes: ErrorAttributes

    @RequestMapping(value = "/error")
    internal fun error(request: HttpServletRequest, response: HttpServletResponse): ErrorJson {
        // Appropriate HTTP response code (e.g. 404 or 500) is automatically set by Spring.
        // Here we just define response body.
        return ErrorJson(response.getStatus(), getErrorAttributes(request, false))
    }

    override fun getErrorPath(): String {
        return PATH
    }

    private fun getErrorAttributes(request: HttpServletRequest, includeStackTrace: Boolean): Map<String, Any> {
        val requestAttributes = ServletWebRequest(request)
        return errorAttributes.getErrorAttributes(requestAttributes, includeStackTrace)
    }

    companion object {

        private val PATH = "/error"
    }

}

class ErrorJson(var status: Int?, errorAttributes: Map<String, Any>) {
    var error: String
    var message: String
    var timeStamp: String
    var trace: String

    init {
        this.error = errorAttributes["error"] as String
        this.message = errorAttributes["message"] as String
        this.timeStamp = errorAttributes["timestamp"].toString()
        this.trace = errorAttributes["trace"] as String
    }

}