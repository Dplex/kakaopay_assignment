package com.kakaopay.assignment.service.predict

import com.kakaopay.assignment.config.FinanceConfig
import com.kakaopay.assignment.const.PreditionType
import com.kakaopay.assignment.repo.model.FinanceVo
import com.kakaopay.assignment.rest.response.PredictionResponse

abstract class IPredict(val financeConfig: FinanceConfig) {

    abstract fun predict(bankCode: String, month: Int, financeLst: List<FinanceVo>): PredictionResponse

    companion object {
        fun getPredictObject(preditionType: String, financeConfig: FinanceConfig): IPredict {
            return when (preditionType) {
                PreditionType.SIMPLE -> SimplePredict(financeConfig)
                PreditionType.STATISTIC -> StatisticPredict(financeConfig)

                else -> StatisticPredict(financeConfig)
            }
        }
    }
}