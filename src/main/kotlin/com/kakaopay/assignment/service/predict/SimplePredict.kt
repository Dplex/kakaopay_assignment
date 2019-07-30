package com.kakaopay.assignment.service.predict

import com.kakaopay.assignment.config.FinanceConfig
import com.kakaopay.assignment.const.BankType
import com.kakaopay.assignment.const.ResponseType
import com.kakaopay.assignment.repo.model.FinanceVo
import com.kakaopay.assignment.rest.response.PredictionResponse

class SimplePredict(financeConfig: FinanceConfig) : IPredict(financeConfig) {

    override fun predict(bankCode: String, month: Int, financeLst: List<FinanceVo>): PredictionResponse {
        val average = financeLst
                .filter { vo -> vo.bankType == bankCode && vo.month == month }
                .map { it.assurancePrice }.average()

        return PredictionResponse(BankType.getBankName(bankCode),
                financeConfig.configuration.predictionTargetYear,
                month,
                average.toInt(),
                ResponseType.KAKAO_SUCCESS)
    }
}