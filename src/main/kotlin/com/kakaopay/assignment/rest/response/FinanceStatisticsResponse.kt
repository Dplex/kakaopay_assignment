package com.kakaopay.assignment.rest.response

import com.fasterxml.jackson.annotation.JsonInclude

data class FinanceStatisticsResponse(
    val name: String,
    val items: List<TotalStatistics>
)

data class TotalStatistics(
    val year: String,
    val total_amount: Int,
    val detail_amount: List<DetailStatistics>

)

data class DetailStatistics(
    val name: String,
    val amount: Int
)

data class BankStatisticsResponse(
    val bank: String,
    val support_amount: List<BankStatistics>
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class BankStatistics(
    val year: Int,
    val bank: String?,
    val amount: Int?
)