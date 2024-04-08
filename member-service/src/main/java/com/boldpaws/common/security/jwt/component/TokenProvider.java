package com.boldpaws.common.security.jwt.component;

import com.boldpaws.common.security.jwt.constants.JwtConstants;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Collections;
import java.util.Date;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@Component
public class TokenProvider implements InitializingBean {
    private final String secret;
    private final long tokenValidityInMilliseconds;
    private static Key key;
    public TokenProvider(@Value("${jwt.secret}") String secret
            , @Value("${jwt.token-validity-in-seconds}") long tokenValidityInMilliseconds) {
        this.secret = secret;
        this.tokenValidityInMilliseconds = tokenValidityInMilliseconds;
    }
    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        key = Keys.hmacShaKeyFor(keyBytes);
    }
    public String createToken(String subject) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + this.tokenValidityInMilliseconds);
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(now)
                .signWith(key, SignatureAlgorithm.HS256)
                .setExpiration(validity)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = findClaimsByJwt(token);
        User user = new User(claims.getSubject(), "", Collections.EMPTY_LIST);
        return new UsernamePasswordAuthenticationToken(user, token, Collections.EMPTY_LIST);
    }

    public Claims findClaimsByJwt(String token) {
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }

    public boolean validateToken(String token) {
        if (token == null) return false;
        try {
            findClaimsByJwt(token);
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.error("[MalformedJWtException Occurred!] {} ", e.getMessage());
            return false;
        } catch (ExpiredJwtException e) {
            log.error("[ExpiredJwtException Occurred!] {} ", e.getMessage());
            return false;
        } catch (UnsupportedJwtException e) {
            log.error("[UnsupportedJwtException Occurred!] {} ", e.getMessage());
            return false;
        }
        return true;
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(JwtConstants.JWT_TOKEN_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
