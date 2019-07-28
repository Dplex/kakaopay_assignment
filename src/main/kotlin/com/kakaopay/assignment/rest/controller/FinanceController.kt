package com.kakaopay.assignment.rest.controller

import com.kakaopay.assignment.const.Contant
import com.kakaopay.assignment.service.FileService
import com.kakaopay.assignment.service.FinanceService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.File
import java.nio.charset.Charset

@RestController
class FinanceController(
        @Autowired val fileService: FileService,
        @Autowired val financeService: FinanceService

) {

    @PostMapping("/uploadHistory")
    @ResponseBody
    fun uploadHistoryFile(@RequestParam("file") file: MultipartFile): ResponseEntity<String> {
        var historyLst = fileService.parseCsvToStringLst(file)

        var financeLst = fileService.convertFinanceVoLst(historyLst)

        financeService.saveFinaceLst(financeLst)

        return ResponseEntity.ok(historyLst.size.toString())
    }

}