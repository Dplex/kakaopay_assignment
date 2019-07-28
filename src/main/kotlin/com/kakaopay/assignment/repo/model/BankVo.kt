package com.kakaopay.assignment.repo.model

import org.apache.ibatis.type.Alias

data class BankVo(
        val bankCode: String,
        val bankName: String
)