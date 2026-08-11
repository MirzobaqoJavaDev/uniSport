package uz.uniSport.uni_sport.repository.payment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.uniSport.uni_sport.domain.payment.Subscription;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Obunalarni (Subscription) boshqarish uchun Repository.
 */
@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    /**
     * Obunani UUID bo'yicha qidirish (API so'rovlarida ishlatiladi).
     * @param uuid obuna tashqi identifikatori
     * @return topilgan obuna
     */
    Optional<Subscription> findByUuid(UUID uuid);

    /**
     * Foydalanuvchiga tegishli barcha obunalarni topish.
     * @param userId foydalanuvchi DB ichki id si
     * @return obunalar ro'yxati
     */
    List<Subscription> findByUserUuid(UUID userId);
}
