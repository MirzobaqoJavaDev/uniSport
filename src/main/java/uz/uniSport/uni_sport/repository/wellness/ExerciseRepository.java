package uz.uniSport.uni_sport.repository.wellness;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.uniSport.uni_sport.domain.wellness.Exercise;

import java.util.Optional;
import java.util.UUID;

/**
 * Mashqlar (Exercise) katalogini boshqarish uchun Repository.
 */
@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

    /**
     * Mashqni UUID bo'yicha qidirish (API so'rovlarida ishlatiladi).
     * @param uuid mashq tashqi identifikatori
     * @return topilgan mashq
     */
    Optional<Exercise> findByUuid(UUID uuid);
    java.util.List<uz.uniSport.uni_sport.domain.wellness.Exercise> findByUuidIn(java.util.Collection<java.util.UUID> uuids);
}
