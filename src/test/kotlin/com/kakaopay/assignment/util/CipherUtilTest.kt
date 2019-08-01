package com.kakaopay.assignment.util

import org.junit.Before
import org.junit.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class CipherUtilTest {

    lateinit var cipherUtil: CipherUtil

    @Before
    fun init() {
        cipherUtil = CipherUtil("mykakaoapitest12")
    }

    @Test
    fun test_encryp_decrypt() {
        //given
        val plainText = "plain"

        //when
        val encText = cipherUtil.encryptCbc(plainText)

        //then
        assert(encText != plainText)


        //given
        val encedText = encText

        //when
        val decryptedText = cipherUtil.decrypteCbc(encedText)

        //then
        assert(decryptedText == plainText)
    }

}