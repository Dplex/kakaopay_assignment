package com.kakaopay.assignment.rest.response

import com.kakaopay.assignment.const.ResponseType

data class PredictionResponse(
        val bankName: String,
        val year: Int,
        val month: Int,
        val amount: Int,
        val responseType: ResponseType
): APIResponse(responseType)