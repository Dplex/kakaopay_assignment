package com.kakaopay.assignment.service

import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

@Service
class JwtUserDetailService: UserDetailsService{

    override fun loadUserByUsername(username: String): UserDetails {
        return User(username, "\$2a\$10\$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6", ArrayList())
    }
}