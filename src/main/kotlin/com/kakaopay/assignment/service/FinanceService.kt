package com.kakaopay.assignment.service

import com.kakaopay.assignment.config.FinanceConfig
import com.kakaopay.assignment.const.BankType
import com.kakaopay.assignment.const.UNKNOWN_CODE
import com.kakaopay.assignment.converter.FinanceConverter
import com.kakaopay.assignment.repo.mapper.FinanceMapper
import com.kakaopay.assignment.repo.model.FinanceVo
import com.kakaopay.assignment.rest.response.BankStatistics
import com.kakaopay.assignment.rest.response.BankStatisticsResponse
import com.kakaopay.assignment.rest.response.FinanceStatisticsResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import kotlin.streams.toList

@Service
class FinanceService(
//        @Autowired val financeRepository: FinanceRepository,
        @Autowired val financeMapper: FinanceMapper,
        @Autowired val financeConfig: FinanceConfig,
        @Autowired val financeConverter: FinanceConverter
) {

    fun test(): ResponseEntity<String> {
//        val items = financeRepository.findByYearAndMonth(2015, 10)
        return ResponseEntity.ok("1")
    }

    fun saveFinaceLst(financeLst: List<FinanceVo>) {
        if (financeConfig.configuration.removeOldHistory) {
            financeMapper.deleteAll()
        }
        financeLst.forEach{ vo -> financeMapper.insertOrUpdate(vo) }

    }

    fun getSummerize(): ResponseEntity<FinanceStatisticsResponse> {
        val summerizeLst = financeMapper.getSummerize(UNKNOWN_CODE)
        val items = summerizeLst.groupBy{ it.year }.toList().stream().map {
            financeConverter.convertTotalStatistics(it.first, it.second)
        }.toList().sortedBy { it.year as String }
        return ResponseEntity.ok(FinanceStatisticsResponse("주택금융 공급현황", items))
//        print(items)
    }

    fun getLargest(code: String): ResponseEntity<BankStatistics> {

        val statisticsVo = financeMapper.getLargest(code)

        return ResponseEntity.ok(BankStatistics(statisticsVo.year, statisticsVo.bankName, null))
    }

    fun getAverageMinMax(code: String): ResponseEntity<BankStatisticsResponse> {
        val statisticsVoLst = financeMapper.getSummerize(code)

        val bankName = BankType.getBankName(code)

        statisticsVoLst.minBy { it.totalPrice }
        statisticsVoLst.maxBy { it.totalPrice }

        return ResponseEntity.ok(BankStatisticsResponse(bankName, listOf(
                financeConverter.convertBankStatistics(statisticsVoLst.minBy { it.totalPrice }!!),
                financeConverter.convertBankStatistics(statisticsVoLst.maxBy { it.totalPrice }!!)
        )))

    }


}