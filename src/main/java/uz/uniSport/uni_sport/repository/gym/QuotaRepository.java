package uz.uniSport.uni_sport.repository.gym;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.uniSport.uni_sport.domain.gym.Quota;

import java.util.Optional;
import java.util.UUID;

/**
 * Kvotalarni (Quota) boshqarish uchun Repository.
 */
@Repository
public interface QuotaRepository extends JpaRepository<Quota, Long> {

    /**
     * Kvotani UUID bo'yicha qidirish (API so'rovlarida ishlatiladi).
     * @param uuid kvota tashqi identifikatori
     * @return topilgan kvota
     */
    Optional<Quota> findByUuid(UUID uuid);

    /**
     * Muayyan rolga tegishli kvotani topish.
     * @param roleId rol DB ichki id si
     * @return topilgan kvota
     */
    Optional<Quota> findByRoleId(Long roleId);
}
