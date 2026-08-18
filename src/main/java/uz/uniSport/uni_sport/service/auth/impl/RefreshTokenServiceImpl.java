package uz.uniSport.uni_sport.service.auth.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import uz.uniSport.uni_sport.domain.auth.RefreshToken;
import uz.uniSport.uni_sport.domain.auth.User;
import uz.uniSport.uni_sport.repository.auth.RefreshTokenRepository;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl {
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${jwt.refresh-expiration-days:7}")
    private long refreshTokenExpiration;

    public RefreshToken createRefreshToken(User user) {
        refreshTokenRepository.deleteByUser(user);
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setToken(generateRefreshToken());
        refreshToken.setExpiresAt(LocalDateTime.now().plusDays(refreshTokenExpiration));
        return refreshTokenRepository.save(refreshToken);
    }
    public RefreshToken verifyExpiration(RefreshToken refreshToken) {
        if (refreshToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            refreshTokenRepository.delete(refreshToken);
            throw new RuntimeException("Refresh token expired");
        }
        return refreshToken;
    }
    public RefreshToken findByToken(String token) {
        return refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Refresh token not found"));
    }
    public void deleteByToken(String refreshToken) {
        refreshTokenRepository.deleteByToken(refreshToken);
    }
    private String generateRefreshToken() {

        byte[] randomBytes = new byte[64];

        new SecureRandom().nextBytes(randomBytes);

        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(randomBytes);
    }

}
