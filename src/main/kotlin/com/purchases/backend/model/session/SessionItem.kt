package com.purchases.backend.model.session

data class SessionItem(
        var token: String? = null,
        var name: String? = null,
        var roles: List<String>? = null
)