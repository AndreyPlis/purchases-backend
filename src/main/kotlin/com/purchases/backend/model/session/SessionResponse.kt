package com.purchases.backend.model.session

import com.purchases.backend.model.response.OperationResponse
import io.swagger.annotations.ApiModelProperty

class SessionResponse(
        @ApiModelProperty(required = true, value = "")
        val sessionItem: SessionItem
) : OperationResponse()