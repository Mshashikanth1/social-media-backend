package com.slip.user.util;

import com.slip.user.Models.User;
import io.jsonwebtoken.*;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.data.neo4j.core.ReactiveNeo4jClient.log;

@UtilityClass
public class JwtTokenUtil {

    private static final String SECRET_KEY = "OTZiMDBmZGQ5YWRhY2I1Y2RjZGRkYjAyYTY2ZmQ3ZWU2YmY4OTAxNzI4YTA2ZmVkODVlOGUzNDNkZGFmYzM0ZjUyMjk4YTgzYjk3ODBiYmRhYmExMWQ2MzZkYWJkZDg3Yjc4Y2FiZjc1YzkxOWEwZjg1NWFiZDFiOTA4ZDBkODE=";
    private static final long EXPIRATION_TIME = 864_000_000; // 10 days

    public static String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getRef());
        claims.put("user_email",user.getEmail());

        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + EXPIRATION_TIME);
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    public static boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }

    public static String getEmailFromToken(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().get("user_email").toString();
    }

    private static Date getExpirationDateFromToken(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getExpiration();
    }

    private static boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
}
