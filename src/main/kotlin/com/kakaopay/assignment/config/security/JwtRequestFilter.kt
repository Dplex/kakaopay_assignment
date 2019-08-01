package com.kakaopay.assignment.config.security

import com.kakaopay.assignment.service.JwtUserDetailService
import com.kakaopay.assignment.util.JwtTokenUtil
import com.kakaopay.assignment.util.LoggerUtil
import io.jsonwebtoken.ExpiredJwtException
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.ServletResponse

@Component
class JwtRequestFilter(
    @Autowired val jwtUserDetailService: JwtUserDetailService,
    @Autowired val jwtTokenUtil: JwtTokenUtil
) : OncePerRequestFilter() {

    private val cLogger = LoggerUtil.logger<JwtRequestFilter>()

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val requestTokenHeader = request.getHeader("Authorization")

        var username: String? = null
        var jwtToken: String? = null
        // JWT Token is in the form "Bearer token". Remove Bearer word and get only the Token
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7)
            try {
                username = jwtTokenUtil.getUsernameFromToken(jwtToken)
            } catch (e: IllegalArgumentException) {

                cLogger.error("Unable to get JWT Token")
            } catch (e: ExpiredJwtException) {

                cLogger.error("JWT Token has expired")
            }
        } else {

            logger.warn("JWT Token does not begin with Bearer String")
        }

        // Once we get the token validate it.
        if (username != null && SecurityContextHolder.getContext().authentication == null) {

            val userDetails = this.jwtUserDetailService.loadUserByUsername(username)

            if (jwtTokenUtil.validateToken(jwtToken!!, userDetails!!)) {

                val usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities()
                )
                usernamePasswordAuthenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)

                SecurityContextHolder.getContext().authentication = usernamePasswordAuthenticationToken
            }
        }

        filterChain.doFilter(request, response as ServletResponse?)
    }
}