package com.kakaopay.assignment.service

import com.kakaopay.assignment.const.ResponseType
import com.kakaopay.assignment.repo.mapper.BankMapper
import com.kakaopay.assignment.rest.response.Bank
import com.kakaopay.assignment.rest.response.BanksResponse
import kotlin.streams.toList
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class BankService(
    @Autowired val bankMapper: BankMapper

) {
    fun getAllBanks(): ResponseEntity<BanksResponse> {
        val items = bankMapper.findAll()
        val bankLst = items.stream().map {
            Bank(it.bankCode, it.bankName)
        }.toList()

        return ResponseEntity.ok(BanksResponse(bankLst, ResponseType.KAKAO_SUCCESS))
    }
}