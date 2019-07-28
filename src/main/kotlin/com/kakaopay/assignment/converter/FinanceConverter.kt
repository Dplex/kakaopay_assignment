package com.kakaopay.assignment.converter

import com.kakaopay.assignment.repo.model.FinanceStatistcsVo
import com.kakaopay.assignment.rest.response.BankStatistics
import com.kakaopay.assignment.rest.response.DetailStatistics
import com.kakaopay.assignment.rest.response.TotalStatistics
import org.springframework.stereotype.Service
import kotlin.streams.toList

@Service
class FinanceConverter {

    fun convertTotalStatistics(year: Int, financeStatisticsLst: List<FinanceStatistcsVo>): TotalStatistics {
        return TotalStatistics("$year 년",
                financeStatisticsLst.sumBy { vo -> vo.totalPrice },
                financeStatisticsLst.stream().map {
                    DetailStatistics(it.bankName, it.totalPrice)
                }.toList().sortedBy { it.name }
        )
    }

    fun convertBankStatistics(financeStatistcsVo: FinanceStatistcsVo): BankStatistics {
        return BankStatistics(financeStatistcsVo.year, null, financeStatistcsVo.totalPrice / 12)
    }

}