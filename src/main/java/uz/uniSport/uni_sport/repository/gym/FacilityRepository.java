package uz.uniSport.uni_sport.repository.gym;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.uniSport.uni_sport.domain.gym.Facility;

import java.util.Optional;
import java.util.UUID;

/**
 * Sport inshootlarini boshqarish uchun Repository.
 */
@Repository
public interface FacilityRepository extends JpaRepository<Facility, Long> {

    /**
     * Sport inshootini UUID bo'yicha qidirish (API so'rovlarida ishlatiladi).
     * @param uuid sport inshooti tashqi identifikatori
     * @return topilgan sport inshooti
     */
    Optional<Facility> findByUuid(UUID uuid);
}
