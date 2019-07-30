package com.kakaopay.assignment.service.predict

import com.kakaopay.assignment.config.FinanceConfig
import com.kakaopay.assignment.const.BankType
import com.kakaopay.assignment.const.ResponseType
import com.kakaopay.assignment.const.UNKNOWN_CODE
import com.kakaopay.assignment.repo.model.FinanceVo
import com.kakaopay.assignment.rest.response.PredictionResponse

class StatisticPredict(financeConfig: FinanceConfig) : IPredict(financeConfig) {

    var bankCode: String = UNKNOWN_CODE
    var month: Int = 0
    lateinit var financeLst: List<FinanceVo>

    var yearDuration: Int = 0
    var targetYear: Int = 0
    lateinit var averageDeltaValues: List<Int>
    lateinit var seasonDeltaValues: List<Int>
    lateinit var piePercentageDeltaValues: List<Int>
    lateinit var predictionWeight: List<Int>
    init {
        yearDuration = financeConfig.configuration.predictionFetchDuration
        targetYear = financeConfig.configuration.predictionTargetYear
        averageDeltaValues = financeConfig.configuration.predictionAverageDeltaWeights
        seasonDeltaValues = financeConfig.configuration.predictionSeasonAverageDeltaWeights
        piePercentageDeltaValues = financeConfig.configuration.predictionWholePiePercentageDeltaWeights
        predictionWeight = financeConfig.configuration.predictionCalculateWeight
    }

    override fun predict(bankCode: String, month: Int, financeLst: List<FinanceVo>): PredictionResponse {
        this.bankCode = bankCode
        this.month = month
        this.financeLst = financeLst

        val eachYearPrediction = getEachYearAverage()
        val seasonPrediction = getSeasonAverage()
        val piePercentagePrediction = getPieAverage()

        val predictionPrice = ((eachYearPrediction * predictionWeight[0]) +
                (seasonPrediction * predictionWeight[1]) +
                (piePercentagePrediction * predictionWeight[2])) / predictionWeight.sum()

        return PredictionResponse(BankType.getBankName(bankCode),
                financeConfig.configuration.predictionTargetYear,
                month,
                predictionPrice.toInt(),
                ResponseType.KAKAO_SUCCESS)
    }

    private fun getPieAverage(): Double {
        val wholePie = financeLst.filter { targetYear - it.year <= yearDuration }
                .groupBy { it.year }
                .mapValues { it.value.sumBy { vo -> vo.assurancePrice } }
        val bankPie = financeLst.filter { it.bankType == bankCode && targetYear - it.year <= yearDuration}
                .groupBy { it.year }
                .mapValues { it.value.sumBy { vo -> vo.assurancePrice } }

        val averagePie = wholePie.toMutableMap().map {
            (bankPie.getOrDefault(it.key, 1) / (it.value * 1.0) * 100).toInt()
        }

        val predictionPiePercentage = getWeightAverageByLast(averagePie.toList(), piePercentageDeltaValues) / 100.0

        val predictionPie = getWeightAverageByLast(wholePie.map { it.value }.toList(), piePercentageDeltaValues)

        return predictionPie * predictionPiePercentage / 12

    }

    private fun getSeasonAverage(): Double {
        val seasonMonthLst = financeConfig.configuration.predictionSeasonMonthLst.first { it -> it.contains(month) }
        val seasonPrice = financeLst
                .filter { vo ->
                    vo.bankType == bankCode && targetYear - vo.year <= yearDuration && seasonMonthLst.contains(vo.month)
                }
                .groupBy(FinanceVo::year).mapValues { entry -> entry.value.sumBy { it.assurancePrice }.div(entry.value.size) }
                .toList().sortedBy { it.first }.map { it.second }

        return getWeightAverageByLast(seasonPrice.toList(), seasonDeltaValues)
    }

    private fun getEachYearAverage(): Double {
        val eachYearPrice = financeLst
                .filter { vo ->
                    vo.bankType == bankCode && targetYear - vo.year <= yearDuration && vo.month == month
                }
                .sortedBy { it.year }.map { it.assurancePrice }
        print(eachYearPrice)
        return getWeightAverageByLast(eachYearPrice.toList(), averageDeltaValues)
    }

    private fun getWeightAverageByLast(priceLst: List<Int>, weightValues: List<Int>): Double {
        var deltaValue = 0.0
        println(priceLst)
        println(weightValues)
        for ((index, value) in weightValues.withIndex()) {
            deltaValue += value * (priceLst[index + 1] * 1.0 / priceLst[index])
        }

        deltaValue /= weightValues.sum()

        return priceLst.last() * deltaValue
    }

}