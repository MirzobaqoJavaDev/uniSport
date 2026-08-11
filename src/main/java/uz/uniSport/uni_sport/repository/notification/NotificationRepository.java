package uz.uniSport.uni_sport.repository.notification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.uniSport.uni_sport.domain.notification.Notification;
import uz.uniSport.uni_sport.domain.notification.NotificationType;

import java.util.Optional;
import java.util.UUID;

/**
 * Bildirishnomalarni (Notification) boshqarish uchun Repository.
 * referenceId — Long (Booking.id kabi DB ichki id ga ishora).
 */
@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    /**
     * Bildirishnomani UUID bo'yicha qidirish (API so'rovlarida ishlatiladi).
     * @param uuid bildirishnoma tashqi identifikatori
     * @return topilgan bildirishnoma
     */
    Optional<Notification> findByUuid(UUID uuid);

    /**
     * Muayyan entity va tur bo'yicha bildirishnoma mavjudligini tekshirish.
     * Bir xil eslatmani ikki marta yubormaslik uchun ishlatiladi.
     * @param referenceId bog'liq entity ning DB ichki id si (Long)
     * @param type bildirishnoma turi
     * @return mavjud bo'lsa true
     */
    boolean existsByReferenceIdAndType(Long referenceId, NotificationType type);
}
