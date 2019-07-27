package com.kakaopay.assignment.service

import com.kakaopay.assignment.repo.FinanceRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class FinanceService(
        @Autowired val financeRepository: FinanceRepository
) {
    fun test(): ResponseEntity<String> {
        val items = financeRepository.findByYearAndMonth(2015, 10)
        return ResponseEntity.ok("1")
    }
}