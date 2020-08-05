package com.depa.user.service.internal;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;
import java.security.Key;

import com.depa.user.model.user.impl.UserVanila;
import static com.depa.user.service.internal.GlobalValue.EXPIRATION_TIME;
import static com.depa.user.service.internal.GlobalValue.secretKey;
import io.jsonwebtoken.Claims;
import javax.servlet.http.HttpServletRequest;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.UnsupportedEncodingException;
import org.springframework.stereotype.Service;

import java.util.HashMap;

import java.util.Base64;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class TokenAuthenticationService {

    public String createTokenUser(UserVanila user) {
        Date now = new Date();
        HashMap<String, Object> userJson = new HashMap<>();
        userJson.put("userId", user.getUserId().toString());
        userJson.put("userType", user.getUserType());
        userJson.put("email", user.getEmail());
        
        String token = Jwts.builder()
                .claim("user", userJson)
                .setIssuedAt(now)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encodeToString(secretKey.getBytes()))
//                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes())
                .compact();
        return token;
    }

    public static Claims validateJWTAuthentication(HttpServletRequest request) {
        System.out.println(secretKey);
        String token = request.getHeader("Authorization");
        try {
            if (token != null) {
                try {
                    Claims user = (Claims) (Jwts.parser().setSigningKey(Base64.getEncoder()
                            .encodeToString(secretKey.getBytes("UTF-8")))
                            .parseClaimsJws(token.replace("Bearer ", ""))
                            .getBody());
                    return user;
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(TokenAuthenticationService.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                throw new BadRequestException("Authorization Header is not found !");
            }
        } catch (io.jsonwebtoken.SignatureException signatureException) {
            throw new BadRequestException("JWT Token has been change we dont trust your token !");
        } catch (io.jsonwebtoken.ExpiredJwtException signExpiredJwtException) {
            throw new BadRequestException("JWT Token Is Already Timeout Please Login Again !");
        }
        return null;
    }

}

