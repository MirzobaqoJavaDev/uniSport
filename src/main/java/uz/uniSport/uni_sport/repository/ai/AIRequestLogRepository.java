package uz.uniSport.uni_sport.repository.ai;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.uniSport.uni_sport.domain.ai.AIRequestLog;

import java.util.List;
import java.util.UUID;

/**
 * AI ga yuborilgan so'rovlar tarixini (AIRequestLog) boshqarish uchun Repository.
 */
@Repository
public interface AIRequestLogRepository extends JpaRepository<AIRequestLog, UUID> {
    /**
     * Muayyan foydalanuvchi tomonidan qilingan barcha AI so'rovlarini qidirish.
     * @param userId foydalanuvchi ID'si
     * @return so'rovlar jurnali ro'yxati
     */
    List<AIRequestLog> findByUserId(UUID userId);
}
