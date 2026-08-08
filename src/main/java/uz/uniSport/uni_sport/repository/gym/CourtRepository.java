package uz.uniSport.uni_sport.repository.gym;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.uniSport.uni_sport.domain.gym.Court;

import java.util.List;
import java.util.UUID;

/**
 * Kortlarni boshqarish uchun Repository.
 */
@Repository
public interface CourtRepository extends JpaRepository<Court, UUID> {
    /**
     * Ma'lum bir sport inshootiga (Facility) tegishli kortlarni topish.
     * @param facilityId sport inshooti ID'si
     * @return kortlar ro'yxati
     */
    List<Court> findByFacilityId(UUID facilityId);
}
