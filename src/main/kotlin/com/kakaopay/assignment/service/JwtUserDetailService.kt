package com.kakaopay.assignment.service

import com.kakaopay.assignment.config.FinanceConfig
import com.kakaopay.assignment.repo.mapper.UserMapper
import com.kakaopay.assignment.repo.model.UserVo
import com.kakaopay.assignment.util.LoggerUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class JwtUserDetailService(
    @Autowired val financeConfig: FinanceConfig,
    @Autowired val userMapper: UserMapper
) : UserDetailsService {

    val logger = LoggerUtil.logger<JwtUserDetailService>()

    override fun loadUserByUsername(username: String): UserDetails? {
        val user = userMapper.getUser(username)
        if (user == null) {
            logger.error("$username user not registed.")
            return null
        }

        return User(username, financeConfig.cipherUtil.decrypteCbc(user.encPassword), ArrayList())
    }

    fun saveUser(username: String, password: String): UserDetails {
        val user = UserVo(username, financeConfig.cipherUtil.encryptCbc(password))
        userMapper.insertUser(user)
        return User(username, user.encPassword, ArrayList())
    }

    fun validCheck(username: String, password: String): UserDetails? {
        val user = userMapper.getUser(username)
        user?.let {
            financeConfig.cipherUtil.decrypteCbc(user.encPassword).run {
                if (this == password) {
                    return User(username, this, ArrayList())
                }
            }
        }
        logger.error("user : $username not valid")
        return null
    }
}