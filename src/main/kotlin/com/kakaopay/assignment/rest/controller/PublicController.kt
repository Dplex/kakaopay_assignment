package com.kakaopay.assignment.rest.controller

import com.kakaopay.assignment.config.FinanceConfig
import com.kakaopay.assignment.const.ResponseType
import com.kakaopay.assignment.rest.request.UserRequest
import com.kakaopay.assignment.rest.response.SimpleResponse
import com.kakaopay.assignment.rest.response.UserResponse
import com.kakaopay.assignment.service.JwtUserDetailService
import com.kakaopay.assignment.util.JwtTokenUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*

@RestController()
@RequestMapping("/public")
class PublicController(
    @Autowired val jwtTokenUtil: JwtTokenUtil,
    @Autowired val jwtUserDetailService: JwtUserDetailService,
    @Autowired val financeConfig: FinanceConfig
) {

    @PostMapping("/signup")
    fun signup(@RequestBody userRequest: UserRequest): Any {
        var userDetails = jwtUserDetailService.loadUserByUsername(userRequest.username)

        userDetails?.run {
            return badRequest()
        }

        userDetails = jwtUserDetailService.saveUser(userRequest.username, userRequest.password)
        return safeRequest(userDetails)
    }

    @PostMapping("/signin")
    fun signin(@RequestBody userRequest: UserRequest): Any {
        var userDetails = jwtUserDetailService.validCheck(userRequest.username, userRequest.password)

        return userDetails?.let { safeRequest(it) } ?: badRequest()
    }

    fun safeRequest(userDetails: UserDetails): ResponseEntity<UserResponse> {
        val token = jwtTokenUtil.generateToken(userDetails)

        return ResponseEntity.ok(UserResponse(token, ResponseType.KAKAO_SUCCESS))
    }

    fun badRequest(): ResponseEntity<SimpleResponse> {
        ResponseType.KAKAO_BAD_REQUEST.run {
            return ResponseEntity(SimpleResponse(this), this.resultStatus)
        }
    }

    @GetMapping("/reload")
    fun reloadConfiguration(): ResponseEntity<SimpleResponse> {
        financeConfig.reload()

        return ResponseEntity(SimpleResponse(ResponseType.KAKAO_ACCEPTED), ResponseType.KAKAO_ACCEPTED.resultStatus)
    }
}