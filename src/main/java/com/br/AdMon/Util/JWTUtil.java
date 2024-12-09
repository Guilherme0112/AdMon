package com.br.AdMon.Util;

import java.security.Key;
import java.util.Date;
import java.util.Map;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

public class JWTUtil {

    private static final String SECRET_KEY = "OU0764BS43d34ksj4D42B52DSs123456";
    private static final int EXPIRATION_TIME = 10 * 60 * 1000;
    private static final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    public static String generateToken(String email, String nome, String senha){
            return Jwts.builder()
                    .setClaims(Map.of("email", email,
                                "nome", nome,
                                "senha", senha))
                    .setSubject(email)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                    .signWith(key, SignatureAlgorithm.HS256)
                    .compact();
    }

    public static Claims extractData(String token) {
        try{
           return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

        } catch (JwtException e){
            return null;
        }
    }

    public static boolean isTokenExpired(String token) {
        Claims claims = extractData(token);
        if (claims != null) {
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        }
            return true; 
        }
}
