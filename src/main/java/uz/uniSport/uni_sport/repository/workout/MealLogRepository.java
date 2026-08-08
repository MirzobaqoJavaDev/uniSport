package uz.uniSport.uni_sport.repository.workout;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.uniSport.uni_sport.domain.workout.MealLog;

import java.util.List;
import java.util.UUID;

/**
 * Ovqatlanish jurnalini (MealLog) boshqarish uchun Repository.
 */
@Repository
public interface MealLogRepository extends JpaRepository<MealLog, UUID> {
    /**
     * Foydalanuvchining ovqatlanish tarixini qidirish.
     * @param userId foydalanuvchi ID'si
     * @return MealLog ro'yxati
     */
    List<MealLog> findByUserId(UUID userId);
}
