package com.kakaopay.assignment.service

import com.kakaopay.assignment.const.ResponseType
import com.kakaopay.assignment.repo.BankRepository
import com.kakaopay.assignment.rest.response.Bank
import com.kakaopay.assignment.rest.response.BanksResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import kotlin.streams.toList

@Service
class BankService(
        @Autowired val bankRepository: BankRepository

) {
    fun getAllBanks(): ResponseEntity<BanksResponse> {

        val items = bankRepository.findAll()
        val bankLst = items.stream().map {
            Bank(it.bankCode, it.bankName)
        }.toList()

        return ResponseEntity.ok(BanksResponse(bankLst, ResponseType.KAKAO_ACCEPTED))
    }

}