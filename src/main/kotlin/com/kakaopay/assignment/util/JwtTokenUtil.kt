package com.kakaopay.assignment.util

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import java.util.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component

@Component
class JwtTokenUtil {

    val logger = LoggerUtil.logger<JwtTokenUtil>()

    val validity = 5 * 60 * 60

    @Value("\${jwt.secret}")
    val secretKey: String? = null

    fun getUsernameFromToken(token: String): String {
        return getAllClaimsFromToken(token).subject
    }

    fun isTokenExpired(token: String): Boolean {
        return getExpirationDateFromToken(token).before(Date())
    }

    fun generateToken(userDetails: UserDetails): String {
        val claims = HashMap<String, Any>()
        val token = doGenerateToken(claims, userDetails.getUsername())
        logger.info("userDetails : ${userDetails} / token : ${token}}")
        return token
    }

    private fun getExpirationDateFromToken(token: String): Date {
        return getAllClaimsFromToken(token).expiration
    }

    private fun getAllClaimsFromToken(token: String): Claims {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).body
    }

    private fun doGenerateToken(claims: Map<String, Any>, subject: String): String {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + validity * 1000))
            .signWith(SignatureAlgorithm.HS512, secretKey).compact()
    }

    fun validateToken(token: String, userDetails: UserDetails): Boolean {
        val username = getUsernameFromToken(token)
        return username == userDetails.getUsername() && !isTokenExpired(token)
    }
}