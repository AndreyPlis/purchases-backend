package com.purchases.backend.model.user

import com.purchases.backend.model.response.OperationResponse


class UserResponse (
        val name: String,
        val password: String,

        val role: Role
): OperationResponse()