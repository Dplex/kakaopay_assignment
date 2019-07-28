package com.kakaopay.assignment.service

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.web.multipart.MultipartFile
import java.io.InputStream
import java.nio.charset.Charset

@SpringBootTest(classes = arrayOf(FileService::class))
class FileServiceTest {

    lateinit var fileService: FileService
    lateinit var file: MultipartFile
    lateinit var inputStream: InputStream

    @Before
    fun init() {
        fileService = FileService()
        inputStream = mock {
            on { readBytes() }
                    .doReturn("1987, 8, 1, 2, 3, 4, 5, 6, 7, 8, 9".toByteArray(Charset.defaultCharset()))
        }
        file = mock {
            on { inputStream }.doReturn(inputStream)
        }
    }

    @Test
    fun parseCsvToStringLstTest1() {

        //given
        val obj = fileService.parseCsvToStringLst(file)


        print(obj)
        //when


        //then
    }

}