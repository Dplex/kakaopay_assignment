package com.kakaopay.assignment.rest.controller

import com.kakaopay.assignment.config.FinanceConfig
import com.kakaopay.assignment.const.PreditionType
import com.kakaopay.assignment.const.ResponseType
import com.kakaopay.assignment.const.UNKNOWN_CODE
import com.kakaopay.assignment.rest.response.*
import com.kakaopay.assignment.service.FileService
import com.kakaopay.assignment.service.FinanceService
import com.kakaopay.assignment.service.PredictionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController()
@RequestMapping("/api/v1")
class FinanceController(
    @Autowired val fileService: FileService,
    @Autowired val financeService: FinanceService,
    @Autowired val predictionService: PredictionService,
    @Autowired val financeConfig: FinanceConfig
) {

    @PostMapping("/uploadHistory")
    @ResponseBody
    fun uploadHistoryFile(@RequestParam("file") file: MultipartFile): ResponseEntity<SimpleResponse> {
        var historyLst = fileService.parseCsvToStringLst(file)

        var financeLst = fileService.convertFinanceVoLst(historyLst)

        financeService.saveFinaceLst(financeLst)

        return ResponseEntity(SimpleResponse(ResponseType.KAKAO_ACCEPTED), ResponseType.KAKAO_ACCEPTED.resultStatus)
    }

    @GetMapping("/statistics/summarize")
    @ResponseBody
    fun getSummerize(): ResponseEntity<FinanceStatisticsResponse> {
        return financeService.getSummerize()
    }

    @GetMapping("/statistics/largest")
    @ResponseBody
    fun getLargest(@RequestParam("code", required = false, defaultValue = UNKNOWN_CODE) code: String):
    ResponseEntity<BankStatistics> {
        return financeService.getLargest(code)
    }

    @GetMapping("/statistics/minmax")
    @ResponseBody
    fun getAverageMinMax(@RequestParam("code", required = false, defaultValue = UNKNOWN_CODE) _code: String):
    ResponseEntity<BankStatisticsResponse> {
        var code = _code
        if (code == UNKNOWN_CODE) code = financeConfig.configuration.api_minmax_default_code
        return financeService.getAverageMinMax(code)
    }

    @GetMapping("/prediction")
    @ResponseBody
    fun predict(
        @RequestParam("code", required = true) code: String,
        @RequestParam("month", required = true) month: Int,
        @RequestParam("mode", required = false, defaultValue = PreditionType.STATISTIC) mode: String
    ): ResponseEntity<PredictionResponse> {

        return predictionService.predict(code, month, mode)
    }
}