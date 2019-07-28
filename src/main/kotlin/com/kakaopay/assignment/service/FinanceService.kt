package com.kakaopay.assignment.service

import com.kakaopay.assignment.config.FinanceConfig
import com.kakaopay.assignment.repo.FinanceRepository
import com.kakaopay.assignment.repo.model.FinanceVo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class FinanceService(
        @Autowired val financeRepository: FinanceRepository,
        @Autowired val financeConfig: FinanceConfig
) {

    fun test(): ResponseEntity<String> {
        val items = financeRepository.findByYearAndMonth(2015, 10)
        return ResponseEntity.ok("1")
    }

    fun saveFinaceLst(financeLst: List<FinanceVo>) {
        if (financeConfig.configuration.removeOldHistory) {
            financeRepository.deleteAll()
        }
        financeLst.forEach{ vo -> financeRepository.merge(vo.year, vo.month, vo.bankType, vo.assurancePrice) }

    }


}