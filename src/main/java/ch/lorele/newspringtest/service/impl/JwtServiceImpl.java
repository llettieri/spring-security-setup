package ch.lorele.newspringtest.service.impl;

import ch.lorele.newspringtest.model.entity.RefreshToken;
import ch.lorele.newspringtest.model.TokenType;
import ch.lorele.newspringtest.model.entity.User;
import ch.lorele.newspringtest.repo.RefreshTokenRepository;
import ch.lorele.newspringtest.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
@Slf4j
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {
    @Value("${jwt.token.signing.key}")
    private String jwtSigningKey;
    @Value("${jwt.token.access.expiration.millis}")
    private Integer accessTokenExpiration;
    @Value("${jwt.token.refresh.expiration.millis}")
    private Integer refreshTokenExpiration;
    private final RefreshTokenRepository refreshTokenRepo;

    @Override
    public String extractEmail(String token) {
        return this.extractClaim(token, Claims::getSubject);
    }

    @Override
    public String generateAccessToken(UserDetails userDetails) {
        return this.generateToken(Map.of("type", TokenType.ACCESS), userDetails, this.accessTokenExpiration);
    }

    @Override
    public String generateRefreshToken(User user) {
        String token = this.generateToken(Map.of("type", TokenType.REFRESH), user, this.refreshTokenExpiration);
        this.refreshTokenRepo.save(RefreshToken.builder().name(token).user(user).build());
        return token;
    }

    @Override
    public boolean isAccessTokenValid(String token, UserDetails userDetails) {
        String email = this.extractEmail(token);

        return email.equals(userDetails.getUsername()) && this.isTokenType(token, TokenType.ACCESS) && !this.isTokenExpired(token);
    }

    @Override
    public boolean isRefreshTokenValid(String token) {
        if (!this.refreshTokenRepo.existsByName(token)) {
            return false;
        }

        if (this.isTokenExpired(token)) {
            this.refreshTokenRepo.deleteByName(token);
            return false;
        }

        return this.isTokenType(token, TokenType.REFRESH);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = this.extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails, int expiration) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusMillis(expiration)))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private boolean isTokenExpired(String token) {
        try {
            return this.extractExpiration(token).before(new Date());
        } catch (ExpiredJwtException e) {
            log.warn("[JwtService] - '{}' {}", token, e.getMessage());
        }

        return true;
    }

    private Date extractExpiration(String token) {
        return this.extractClaim(token, Claims::getExpiration);
    }

    private boolean isTokenType(String token, TokenType type) {
        return this.extractClaim(token, claims -> claims.get("type")).equals(type);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey() {
        byte[] bites = Decoders.BASE64.decode(jwtSigningKey);
        return Keys.hmacShaKeyFor(bites);
    }
}
