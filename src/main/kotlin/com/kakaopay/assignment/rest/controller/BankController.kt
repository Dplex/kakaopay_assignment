package com.kakaopay.assignment.rest.controller

import com.kakaopay.assignment.rest.response.BanksResponse
import com.kakaopay.assignment.service.BankService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
class BankController(
        @Autowired val bankService: BankService
) {

    @GetMapping("/banks")
    @ResponseBody
    fun getBankLst(): ResponseEntity<BanksResponse> {
        return bankService.getAllBanks()
    }

}