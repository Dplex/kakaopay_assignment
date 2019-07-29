package com.kakaopay.assignment.config

import com.kakaopay.assignment.const.BankType

class Configuration {
    var api_minmax_default_code = BankType.KEB.bankCode
    var removeOldHistory = false

    var predictionTargetYear: Int = 2018
    var predictionFetchDuration = 5
    var predictionAverageDeltaWeights = listOf(1, 2, 3, 4)
    var predictionSeasonMonthLst = listOf(listOf(1, 2), listOf(3, 4, 5), listOf(6, 7, 8), listOf(9, 10, 11), listOf(12))
    var predictionSeasonAverageDeltaWeights = listOf(1, 2, 3, 4)
    var predictionWholePiePercentageDeltaWeights = listOf(1, 1, 1, 1)
    var predictionCalculateWeight = listOf(2, 3, 5)
}
