package com.kakaopay.assignment.util

import org.slf4j.Logger
import org.slf4j.LoggerFactory

class LoggerUtil {
    companion object {
        inline fun <reified T> logger(): Logger {
            return LoggerFactory.getLogger(T::class.java)
        }
    }
}