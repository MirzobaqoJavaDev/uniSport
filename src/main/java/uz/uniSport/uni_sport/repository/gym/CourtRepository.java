package uz.uniSport.uni_sport.repository.gym;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.uniSport.uni_sport.domain.gym.Court;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Kortlarni boshqarish uchun Repository.
 */
@Repository
public interface CourtRepository extends JpaRepository<Court, Long> {

    /**
     * Kortni UUID bo'yicha qidirish (API so'rovlarida ishlatiladi).
     * @param uuid kort tashqi identifikatori
     * @return topilgan kort
     */
    Optional<Court> findByUuid(UUID uuid);

    /**
     * Ma'lum bir sport inshootiga (Facility) tegishli kortlarni topish.
     * @param facilityId sport inshooti DB ichki id si
     * @return kortlar ro'yxati
     */
    List<Court> findByFacilityUuid(UUID facilityId);
}
