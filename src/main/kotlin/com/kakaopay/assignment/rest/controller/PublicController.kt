package com.kakaopay.assignment.rest.controller

import com.kakaopay.assignment.const.ResponseType
import com.kakaopay.assignment.rest.request.UserRequest
import com.kakaopay.assignment.rest.response.UserResponse
import com.kakaopay.assignment.service.JwtUserDetailService
import com.kakaopay.assignment.util.JwtTokenUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class PublicController(
        @Autowired val jwtTokenUtil: JwtTokenUtil,
        @Autowired val jwtUserDetailService: JwtUserDetailService
) {

    @PostMapping("/signup")
    fun signup(@RequestBody userRequest: UserRequest): ResponseEntity<UserResponse> {
        val userDetails = jwtUserDetailService.loadUserByUsername(userRequest.username)

        val token = jwtTokenUtil.generateToken(userDetails)

        return ResponseEntity.ok(UserResponse(token, ResponseType.KAKAO_SUCCESS))

    }

}