package uz.uniSport.uni_sport.repository.payment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.uniSport.uni_sport.domain.payment.SubscriptionPlan;

import java.util.Optional;
import java.util.UUID;

/**
 * Obuna paketlarini (SubscriptionPlan) boshqarish uchun Repository.
 */
@Repository
public interface SubscriptionPlanRepository extends JpaRepository<SubscriptionPlan, Long> {

    /**
     * Obuna paketini UUID bo'yicha qidirish (API so'rovlarida ishlatiladi).
     * @param uuid obuna paketi tashqi identifikatori
     * @return topilgan obuna paketi
     */
    Optional<SubscriptionPlan> findByUuid(UUID uuid);
}
