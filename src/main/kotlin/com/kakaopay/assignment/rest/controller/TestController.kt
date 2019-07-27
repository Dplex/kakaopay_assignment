package com.kakaopay.assignment.rest.controller

import com.kakaopay.assignment.service.FinanceService
import org.apache.coyote.Response
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController(
        @Autowired val financeService: FinanceService
) {
    @GetMapping("/test")
    @ResponseBody
    fun test(): ResponseEntity<String> {
        return financeService.test()
    }
}