package uz.uniSport.uni_sport.repository.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.uniSport.uni_sport.domain.auth.RefreshToken;
import uz.uniSport.uni_sport.domain.auth.User;

import java.util.Optional;
import java.util.UUID;

/**
 * Refresh tokenlarni boshqarish uchun Repository.
 */
@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {
    /**
     * Token matni bo'yicha qidirish.
     * @param token token string
     * @return topilgan refresh token
     */
    Optional<RefreshToken> findByToken(String token);

    /**
     * Foydalanuvchiga tegishli barcha refresh tokenlarni o'chirish (tizimdan barcha qurilmalardan chiqish).
     * @param user foydalanuvchi
     */
    void deleteByUser(User user);
}
