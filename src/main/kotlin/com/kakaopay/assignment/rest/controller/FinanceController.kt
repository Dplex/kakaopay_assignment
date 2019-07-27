package com.kakaopay.assignment.rest.controller

import com.kakaopay.assignment.const.Contant
import com.kakaopay.assignment.service.FileService
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
        @Autowired val fileService: FileService
) {

    @PostMapping("/uploadHistory")
    @ResponseBody
    fun uploadHistoryFile(@RequestParam("file") file: MultipartFile): ResponseEntity<String> {
        var historyLst = fileService.parseCsvToStringLst(file)
        historyLst.removeAt(Contant.CSV_HEADER_IDX)



//        fileService.uploadCSVFile(file)

        return ResponseEntity.ok(historyLst.size.toString())
    }

}