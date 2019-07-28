package com.kakaopay.assignment.repo.provider

import com.kakaopay.assignment.const.UNKNOWN_CODE
import com.kakaopay.assignment.repo.model.FinanceVo
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.jdbc.SQL

class FinanceProvider {

    fun deleteAll(): String {
        return SQL().DELETE_FROM(HISTORY_TABLE).toString()
    }

    fun insertOrUpdate(@Param("vo") vo: FinanceVo): String {
        return SQL()
                .INSERT_INTO(HISTORY_TABLE)
                .INTO_COLUMNS("year", "month", "bank_type", "asur_price")
                .INTO_VALUES("#{vo.year}", "#{vo.month}", "#{vo.bankType}", "#{vo.assurancePrice}")
                .toString() +
                "ON CONFLICT ON CONSTRAINT tasure_hist_uq01 " +
                "DO UPDATE " +
                "SET asur_price = #{vo.assurancePrice}"
    }

    fun getSummerize(@Param("code") bankType: String): String {
        return SQL()
                .SELECT("year", "bnk_nm bankName", "sum(asur_price) totalPrice")
                .FROM("$HISTORY_TABLE hist")
                .JOIN("${BankProvider.BANK_TABLE} bank ON hist.bank_type = bank.bnk_cd" +
                        if (bankType != UNKNOWN_CODE) " AND bank.bnk_cd = #{code}" else "")
                .GROUP_BY("year", "bankName")
                .toString()
    }

    fun getLargest(@Param("code") bankType: String): String {
        return SQL()
                .SELECT("year", "bnk_nm bankName", "sum(asur_price) totalPrice")
                .FROM("$HISTORY_TABLE hist")
                .JOIN("${BankProvider.BANK_TABLE} bank ON hist.bank_type = bank.bnk_cd" +
                        if (bankType != UNKNOWN_CODE) " AND bank.bnk_cd = #{code}" else "")
                .GROUP_BY("year", "bankName")
                .ORDER_BY("totalPrice desc")
                .LIMIT(1)
                .toString()
    }

    companion object {
        const val HISTORY_TABLE = "kakao_finance_be.tasur_hist"
        const val METHOD_DELETEALL = "deleteAll"
        const val METHOD_INSERT_OR_UPDATE = "insertOrUpdate"
        const val METHOD_GET_SUMMERIZE = "getSummerize"
        const val METHOD_GET_LARGEST = "getLargest"
    }

}