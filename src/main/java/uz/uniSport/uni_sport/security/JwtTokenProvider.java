package uz.uniSport.uni_sport.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt.secret:dGhpcy1pcy1hLXZlcnktc2VjdXJlLWFuZC1sb25nLXNlY3JldC1rZXktdGhhdC1tdXN0LWJlLWF0LWxlYXN0LTMyLWJ5dGVzLWxvbmc=}")
    private String jwtSecret;

    @Value("${app.jwt.expiration-milliseconds:86400000}")
    private long jwtExpirationDate;

    // Token yaratish (Login qilinganda)
    public String generateToken(Authentication authentication) {
        String username = authentication.getName(); // Bu yerda email keladi
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);

        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(expireDate)
                .signWith(key())
                .compact();
    }

    private SecretKey key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    // Tokendan username (email) ni olish
    public String getUsername(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(key())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();
    }

    // Token yaroqli ekanligini tekshirish
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(key())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (MalformedJwtException ex) {
            System.err.println("Yaroqsiz JWT token");
        } catch (ExpiredJwtException ex) {
            System.err.println("Muddati o'tgan JWT token");
        } catch (UnsupportedJwtException ex) {
            System.err.println("Qo'llab-quvvatlanmaydigan JWT token");
        } catch (IllegalArgumentException ex) {
            System.err.println("JWT claims string bo'sh");
        }
        return false;
    }
}
