package com.kakaopay.assignment.rest.response

import com.fasterxml.jackson.annotation.JsonIgnore
import com.kakaopay.assignment.const.ResponseType
import org.springframework.http.HttpStatus

abstract class APIResponse(val message: String, val code: String) {
    @JsonIgnore
    lateinit var status: HttpStatus
    constructor(responseType: ResponseType) : this(responseType.resultCode, responseType.resutMessage) {
        this.status = responseType.resultStatus
    }
}

data class SimpleResponse(
    @JsonIgnore
    val responseType: ResponseType
) : APIResponse(responseType)