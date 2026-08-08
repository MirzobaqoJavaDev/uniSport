package uz.uniSport.uni_sport.repository.pe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.uniSport.uni_sport.domain.pe.ClassSchedule;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Jismoniy tarbiya darslarining jadvallarini (ClassSchedule) boshqarish uchun Repository.
 */
@Repository
public interface ClassScheduleRepository extends JpaRepository<ClassSchedule, UUID> {
    /**
     * Ma'lum bir vaqt oralig'idagi barcha darslarni qidirish.
     * @param start boshlanish vaqti
     * @param end tugash vaqti
     * @return darslar ro'yxati
     */
    List<ClassSchedule> findByStartTimeBetween(LocalDateTime start, LocalDateTime end);
}
