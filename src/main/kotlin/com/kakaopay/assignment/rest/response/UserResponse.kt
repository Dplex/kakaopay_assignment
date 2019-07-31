package com.kakaopay.assignment.rest.response

import com.fasterxml.jackson.annotation.JsonIgnore
import com.kakaopay.assignment.const.ResponseType

data class UserResponse(
    val token: String,
    @JsonIgnore
    val responseType: ResponseType
) : APIResponse(responseType)