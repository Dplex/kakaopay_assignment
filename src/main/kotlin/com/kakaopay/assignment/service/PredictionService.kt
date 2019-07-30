package com.kakaopay.assignment.service

import com.kakaopay.assignment.config.FinanceConfig
import com.kakaopay.assignment.repo.mapper.FinanceMapper
import com.kakaopay.assignment.rest.response.PredictionResponse
import com.kakaopay.assignment.service.predict.IPredict
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class PredictionService(
        @Autowired val financeConfig: FinanceConfig,
        @Autowired val financeMapper: FinanceMapper

) {

    fun predict(bankCode: String, month: Int, mode: String): ResponseEntity<PredictionResponse> {

        val historyLst = financeMapper.getAll()

        IPredict.getPredictObject(mode, financeConfig).run {
            return ResponseEntity.ok(this.predict(bankCode, month, historyLst))
        }
    }


}