package com.kakaopay.assignment.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class FinanceConfig {

    lateinit var configuration: Configuration

    @Value("\${database.url}")
    lateinit var dbUrl: String

    @Value("\${database.username}")
    lateinit var dbUser: String

    @Value("\${database.password}")
    lateinit var dbPassword: String

    @PostConstruct
    fun init() {
        configuration = Configuration()
    }

}