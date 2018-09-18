package com.purchases.backend.model.response

import io.swagger.annotations.ApiModelProperty

open class OperationResponse {
    @ApiModelProperty(required = true)
    lateinit var operationStatus: ResponseStatusEnum
    lateinit var operationMessage: String
}

enum class ResponseStatusEnum {
    SUCCESS, ERROR, WARNING, NO_ACCESS
}