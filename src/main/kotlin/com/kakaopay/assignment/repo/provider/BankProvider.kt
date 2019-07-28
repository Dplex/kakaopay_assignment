package com.kakaopay.assignment.repo.provider

import org.apache.ibatis.jdbc.SQL

class BankProvider {

    fun findAll(): String {
        return SQL()
                .SELECT("bnk_cd bankCode, bnk_nm bankName")
                .FROM(BANK_TABLE)
                .toString()
    }

    companion object {
        const val BANK_TABLE = "kakao_finance_be.tasur_bnk"
        const val METHOD_FINDALL = "findAll"
    }

}