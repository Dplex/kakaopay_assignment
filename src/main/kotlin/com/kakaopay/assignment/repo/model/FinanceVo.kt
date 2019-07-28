package com.kakaopay.assignment.repo.model

data class FinanceVo(
        val year: Int,
        val month: Int,
        val bankType: String,
        val assurancePrice: Int
)