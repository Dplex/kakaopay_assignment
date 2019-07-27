package com.kakaopay.assignment.service

import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.BufferedInputStream
import java.nio.charset.Charset

@Service
class FileService {

    @Async
    fun uploadCSVFile(file: MultipartFile) {

    }

    fun parseCsvToStringLst(file: MultipartFile): MutableList<String> {
        BufferedInputStream(file.inputStream).readBytes()
                .toString(Charset.defaultCharset()).run {
                     return this.split("\n").toMutableList()
                }
    }

}