package uz.uniSport.uni_sport.repository.workout;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.uniSport.uni_sport.domain.workout.MealLog;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Ovqatlanish jurnalini (MealLog) boshqarish uchun Repository.
 */
@Repository
public interface MealLogRepository extends JpaRepository<MealLog, Long> {

    /**
     * Ovqatlanish yozuvini UUID bo'yicha qidirish (API so'rovlarida ishlatiladi).
     * @param uuid yozuv tashqi identifikatori
     * @return topilgan yozuv
     */
    Optional<MealLog> findByUuid(UUID uuid);

    /**
     * Foydalanuvchining ovqatlanish tarixini qidirish.
     * @param userId foydalanuvchi DB ichki id si
     * @return MealLog ro'yxati
     */
    List<MealLog> findByUserUuid(UUID userId);
}
