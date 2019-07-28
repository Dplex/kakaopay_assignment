package com.kakaopay.assignment.rest.controller

import com.kakaopay.assignment.config.FinanceConfig
import com.kakaopay.assignment.const.UNKNOWN_CODE
import com.kakaopay.assignment.rest.response.BankStatistics
import com.kakaopay.assignment.rest.response.BankStatisticsResponse
import com.kakaopay.assignment.rest.response.FinanceStatisticsResponse
import com.kakaopay.assignment.service.FileService
import com.kakaopay.assignment.service.FinanceService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
class FinanceController(
        @Autowired val fileService: FileService,
        @Autowired val financeService: FinanceService,
        @Autowired val financeConfig: FinanceConfig

) {

    @PostMapping("/uploadHistory")
    @ResponseBody
    fun uploadHistoryFile(@RequestParam("file") file: MultipartFile): ResponseEntity<String> {
        var historyLst = fileService.parseCsvToStringLst(file)

        var financeLst = fileService.convertFinanceVoLst(historyLst)

        financeService.saveFinaceLst(financeLst)

        return ResponseEntity.ok(historyLst.size.toString())
    }

    @GetMapping("/statistics/summerize")
    @ResponseBody
    fun getSummerize(): ResponseEntity<FinanceStatisticsResponse> {
        return financeService.getSummerize()
    }

    @GetMapping("/statistics/largest")
    @ResponseBody
    fun getLargest(@RequestParam("code", required = false, defaultValue = UNKNOWN_CODE) code: String)
            : ResponseEntity<BankStatistics>{
        return financeService.getLargest(code)
    }

    @GetMapping("/statistics/minmax")
    @ResponseBody
    fun getAverageMinMax(@RequestParam("code", required = false, defaultValue = UNKNOWN_CODE) _code: String)
            : ResponseEntity<BankStatisticsResponse> {
        var code = _code
        if (code == UNKNOWN_CODE) code = financeConfig.configuration.API_MIN_MAX_DEFAULT_CODE
        return financeService.getAverageMinMax(code)
    }
}