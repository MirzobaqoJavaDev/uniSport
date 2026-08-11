package uz.uniSport.uni_sport.repository.integration;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.uniSport.uni_sport.domain.integration.DynamicQRCode;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Dinamik QR kodlarni (DynamicQRCode) boshqarish uchun Repository.
 */
@Repository
public interface DynamicQRCodeRepository extends JpaRepository<DynamicQRCode, Long> {

    /**
     * QR kodni UUID bo'yicha qidirish (API so'rovlarida ishlatiladi).
     * @param uuid QR kod tashqi identifikatori
     * @return topilgan QR kod
     */
    Optional<DynamicQRCode> findByUuid(UUID uuid);

    /**
     * Muayyan foydalanuvchiga tegishli barcha QR kodlarni topish.
     * @param userId foydalanuvchi DB ichki id si
     * @return QR kodlar ro'yxati
     */
    List<DynamicQRCode> findByUserUuid(UUID userId);
}
