package uz.uniSport.uni_sport.repository.wellness;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.uniSport.uni_sport.domain.wellness.WorkoutPlan;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Mashg'ulot rejalarini (WorkoutPlan) boshqarish uchun Repository.
 */
@Repository
public interface WorkoutPlanRepository extends JpaRepository<WorkoutPlan, Long> {

    /**
     * Mashg'ulot rejasini UUID bo'yicha qidirish (API so'rovlarida ishlatiladi).
     * @param uuid mashg'ulot rejasi tashqi identifikatori
     * @return topilgan mashg'ulot rejasi
     */
    Optional<WorkoutPlan> findByUuid(UUID uuid);

    /**
     * Foydalanuvchiga tegishli barcha mashg'ulot rejalarini topish.
     * @param userId foydalanuvchi DB ichki id si
     * @return mashg'ulot rejalari ro'yxati
     */
    List<WorkoutPlan> findByUserUuid(UUID userId);
}
