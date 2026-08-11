package uz.uniSport.uni_sport.repository.payment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.uniSport.uni_sport.domain.payment.PaymentTransaction;

import java.util.Optional;
import java.util.UUID;

/**
 * To'lov tranzaksiyalarini (PaymentTransaction) boshqarish uchun Repository.
 */
@Repository
public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, Long> {

    /**
     * To'lov tranzaksiyasini UUID bo'yicha qidirish (API so'rovlarida ishlatiladi).
     * @param uuid tranzaksiya tashqi identifikatori
     * @return topilgan tranzaksiya
     */
    Optional<PaymentTransaction> findByUuid(UUID uuid);

    /**
     * Idempotency key bo'yicha tranzaksiyani qidirish (takroriy to'lovlarni oldini olish).
     * @param idempotencyKey takrorlanmas kalit
     * @return topilgan tranzaksiya
     */
    Optional<PaymentTransaction> findByIdempotencyKey(String idempotencyKey);
}
