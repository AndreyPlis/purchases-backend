package com.purchases.backend.model.user

import io.swagger.annotations.ApiModelProperty


data class Login(
        @ApiModelProperty(example = "demo", required = true)
        val username: String = "",

        @ApiModelProperty(example = "demo", required = true)
        val password: String = ""
)