package com.kakaopay.assignment.rest.response

import com.kakaopay.assignment.const.ResponseType

data class UserResponse(
        val token: String,
        val responseType: ResponseType
): APIResponse(responseType)