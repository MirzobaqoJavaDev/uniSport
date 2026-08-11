package uz.uniSport.uni_sport.repository.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.uniSport.uni_sport.domain.auth.RefreshToken;
import uz.uniSport.uni_sport.domain.auth.User;

import java.util.Optional;

/**
 * Refresh tokenlarni boshqarish uchun Repository.
 * RefreshToken BaseEntity dan voris olmaydi — shuning uchun Long id ishlatiladi.
 */
@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

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
