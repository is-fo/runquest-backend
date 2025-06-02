package org.example.auth.util

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import java.util.Date

class TokenUtil {

    val base64Key = System.getenv("MOTH_JWT_SECRET")
    val SECRET = Keys.hmacShaKeyFor(Decoders.BASE64.decode(base64Key))

    fun generateToken(userId: String) : String {
        return Jwts.builder()
            .subject(userId)
            .issuedAt(Date())
            .expiration(Date(System.currentTimeMillis() + 3600000))
            .signWith(SECRET)
            .compact()
    }

    fun parseToken(token: String) : String {
        return Jwts.parser()
            .verifyWith(SECRET)
            .build()
            .parseSignedClaims(token).toString()
    }

    fun getUserIdFromToken(token: String) : String {
        return Jwts.parser()
            .verifyWith(SECRET)
            .build()
            .parseSignedClaims(token)
            .payload
            .subject
    }

}