package com.kakaopay.assignment.service

import com.kakaopay.assignment.const.BankType
import com.kakaopay.assignment.const.Contant
import com.kakaopay.assignment.repo.model.FinanceVo
import com.kakaopay.assignment.util.LoggerUtil
import java.io.BufferedInputStream
import java.nio.charset.Charset
import kotlin.streams.toList
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class FileService {

    val logger = LoggerUtil.logger<FileService>()

    fun parseCsvToStringLst(file: MultipartFile): MutableList<String> {
        BufferedInputStream(file.inputStream).readBytes()
            .toString(Charset.defaultCharset()).run {
            return this.replace("\r", "").split("\n").toMutableList().apply {
                removeAt(Contant.CSV_HEADER_IDX)
            }
        }
    }

    fun convertFinanceVoLst(stringLst: List<String>): List<FinanceVo> {
        val bankLst = BankType.getBankTypeLst()
        val lst = stringLst.stream().map {
            val rowLst = it.split(",")
            val financeLst = ArrayList<FinanceVo>()
            if (rowLst.size == 11) {
                val year = rowLst[0].toInt()
                val month = rowLst[1].toInt()
                for ((idx, value) in bankLst.withIndex()) {
                    financeLst.add(FinanceVo(year.toInt(), month.toInt(), value.bankCode, rowLst[idx + 2].toInt()))
                }
            } else {
                logger.error("rowList length is not 11 -> ${rowLst.size}")
            }
            financeLst
        }.toList().flatten()

        return lst!!
    }
}