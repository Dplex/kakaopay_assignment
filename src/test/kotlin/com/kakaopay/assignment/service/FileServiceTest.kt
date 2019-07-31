package com.kakaopay.assignment.service

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import org.junit.Assert
import java.io.InputStream
import java.nio.charset.Charset
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.web.multipart.MultipartFile

@SpringBootTest(classes = arrayOf(FileService::class))
class FileServiceTest {

    lateinit var fileService: FileService
    lateinit var file: MultipartFile
    lateinit var inputStream: InputStream

    @Before
    fun init() {
        fileService = FileService()
    }

    @Test
    fun parseCsvToStringLstTest1() {

        // given
        val obj = fileService.convertFinanceVoLst(listOf("1987,8,1,2,3,4,5,6,7,8,9"))

        // when
        print(obj)


        // then
        Assert.assertTrue(1==1)
    }
}