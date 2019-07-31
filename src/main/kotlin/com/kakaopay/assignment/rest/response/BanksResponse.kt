package com.kakaopay.assignment.rest.response

import com.fasterxml.jackson.annotation.JsonIgnore
import com.kakaopay.assignment.const.ResponseType

data class BanksResponse(
    val bankList: List<Bank>,
    @JsonIgnore
    val responseType: ResponseType
) : APIResponse(responseType)

data class Bank(val bankCode: String, val bankName: String)