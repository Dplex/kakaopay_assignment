package com.kakaopay.assignment.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.kakaopay.assignment.util.CipherUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Component
import java.io.InputStreamReader
import javax.annotation.PostConstruct

@Component
class FinanceConfig() {

    @Value("\${config.location:classpath:config.json}")
    val configResouce: Resource?= null

    lateinit var configuration: Configuration

    @Value("\${database.url}")
    lateinit var dbUrl: String

    @Value("\${database.username}")
    lateinit var dbUser: String

    @Value("\${database.encPassword}")
    lateinit var encDbPassword: String

    @Value("\${secretKey}")
    lateinit var secretKey: String

    lateinit var dbPassword: String

    @PostConstruct
    fun init() {
        if (configResouce == null) {
            throw Exception("config location error")
        } else {
            dbPassword = CipherUtil(secretKey).decrypteCbc(encDbPassword)
            reload()

        }
    }

    private fun reload() {
        ObjectMapper().run {
            val tempConfiguration =
                    this.readValue(InputStreamReader(configResouce!!.inputStream), Configuration::class.java)
            this@FinanceConfig.configuration = tempConfiguration
        }
    }

}