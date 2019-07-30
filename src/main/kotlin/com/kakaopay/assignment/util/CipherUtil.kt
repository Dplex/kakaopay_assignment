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
import java.util.*
import kotlin.experimental.and


class CipherUtil(val key: String) {

    lateinit var cipher: Cipher
    lateinit var keySpec: SecretKeySpec
    lateinit var gcmSpec: GCMParameterSpec
    lateinit var encoder: Base64.Encoder
    lateinit var decoder: Base64.Decoder

    init {
        cipher = Cipher.getInstance("AES/GCM/NoPadding")
        keySpec = SecretKeySpec(key!!.toByteArray(), "AES")
        gcmSpec = GCMParameterSpec(128, key!!.toByteArray())
        encoder = Base64.getEncoder()
        decoder = Base64.getDecoder()
    }

    fun encryptCbc(text: ByteArray): ByteArray {
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, gcmSpec)
        return encoder.encode(cipher.doFinal(text))
    }

    fun decrypteCbc(text: String): String {

        cipher.init(Cipher.DECRYPT_MODE, keySpec, gcmSpec)
        return cipher.doFinal(decoder.decode(text.toByteArray(Charset.defaultCharset()))).toString(Charset.defaultCharset())
    }
}
