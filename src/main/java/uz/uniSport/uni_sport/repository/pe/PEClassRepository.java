package uz.uniSport.uni_sport.repository.pe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.uniSport.uni_sport.domain.pe.PEClass;

import java.util.Optional;
import java.util.UUID;

/**
 * Jismoniy tarbiya darslarini (PEClass) boshqarish uchun Repository.
 */
@Repository
public interface PEClassRepository extends JpaRepository<PEClass, Long> {

    /**
     * JT darsini UUID bo'yicha qidirish (API so'rovlarida ishlatiladi).
     * @param uuid JT darsi tashqi identifikatori
     * @return topilgan JT darsi
     */
    Optional<PEClass> findByUuid(UUID uuid);
}
