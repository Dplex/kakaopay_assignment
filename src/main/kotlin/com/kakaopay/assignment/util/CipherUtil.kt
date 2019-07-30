package com.kakaopay.assignment.util

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.io.BufferedWriter
import java.io.File
import java.nio.charset.Charset
import javax.crypto.Cipher
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.SecretKeySpec
import org.apache.coyote.http11.Constants.a
import kotlin.experimental.and


@Component
class CipherUtil {

    @Value("\${secretKey:/CipherSecretKey}")
    val key: String?= "/CipherSecretKey"

    lateinit var cipher: Cipher
    lateinit var keySpec: SecretKeySpec
    lateinit var gcmSpec: GCMParameterSpec

    init {
        cipher = Cipher.getInstance("AES/GCM/NoPadding")
        keySpec = SecretKeySpec(key!!.toByteArray(), "AES")
        gcmSpec = GCMParameterSpec(128, key!!.toByteArray())
    }

    fun encryptCbc(text: ByteArray): ByteArray {
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, gcmSpec)
        return cipher.doFinal(text)
    }

    fun decrypteCbc(text: String): String {
        cipher.init(Cipher.DECRYPT_MODE, keySpec, gcmSpec)
        return cipher.doFinal(text.toByteArray(Charset.defaultCharset())).toString(Charset.defaultCharset())
    }
}

fun main() {
    val cipherUtil = CipherUtil()

    val pw = cipherUtil.encryptCbc("!q2w3e4r".toByteArray(Charset.defaultCharset()))
    println(pw.toString(Charset.defaultCharset()))
//    val plain = cipherUtil.decrypteCbc(pw)
//    println(plain.toString(Charset.defaultCharset()))
}
