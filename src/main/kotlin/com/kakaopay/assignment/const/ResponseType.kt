package com.kakaopay.assignment.const

import org.springframework.http.HttpStatus

enum class ResponseType(val resultCode: String, val resutMessage: String, val resultStatus: HttpStatus) {
    KAKAO_SUCCESS("KA2001", "SUCEESS", HttpStatus.OK),
    KAKAO_ACCEPTED("KA2021", "ACCEPTED", HttpStatus.ACCEPTED)
}