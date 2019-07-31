package com.kakaopay.assignment.util

import java.nio.charset.Charset
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.SecretKeySpec

class CipherUtil(val key: String) {

    lateinit var encCipher: Cipher
    lateinit var decCipher: Cipher
    lateinit var keySpec: SecretKeySpec
    lateinit var gcmSpec: GCMParameterSpec
    lateinit var encoder: Base64.Encoder
    lateinit var decoder: Base64.Decoder

    init {
        keySpec = SecretKeySpec(key!!.toByteArray(), "AES")
        gcmSpec = GCMParameterSpec(128, key!!.toByteArray())
        encoder = Base64.getEncoder()
        decoder = Base64.getDecoder()
    }

    fun encryptCbc(byteArray: ByteArray): ByteArray {
        encCipher = Cipher.getInstance("AES/GCM/NoPadding")
        encCipher.init(Cipher.ENCRYPT_MODE, keySpec, gcmSpec)
        return encoder.encode(encCipher.doFinal(byteArray))
    }

    fun encryptCbc(text: String): String {
        return this.encryptCbc(text.toByteArray(Charset.defaultCharset())).toString(Charset.defaultCharset())
    }

    fun decrypteCbc(text: String): String {
        decCipher = Cipher.getInstance("AES/GCM/NoPadding")
        decCipher.init(Cipher.DECRYPT_MODE, keySpec, gcmSpec)
        return decCipher.doFinal(decoder.decode(text.toByteArray(Charset.defaultCharset()))).toString(Charset.defaultCharset())
    }
}
