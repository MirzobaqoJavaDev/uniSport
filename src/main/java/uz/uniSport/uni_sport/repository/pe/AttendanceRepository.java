package uz.uniSport.uni_sport.repository.pe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.uniSport.uni_sport.domain.pe.Attendance;

import java.util.List;
import java.util.UUID;

/**
 * Davomat (Attendance) yozuvlarini boshqarish uchun Repository.
 */
@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, UUID> {
    /**
     * Ma'lum bir dars jadvaliga (schedule) tegishli bo'lgan barcha davomatlarni qidirish.
     * @param scheduleId dars jadvali ID'si
     * @return davomatlar ro'yxati
     */
    List<Attendance> findByScheduleId(UUID scheduleId);

    /**
     * Ma'lum bir talabaning (User) barcha davomatlarini qidirish.
     * @param userId talaba ID'si
     * @return davomatlar ro'yxati
     */
    List<Attendance> findByUserId(UUID userId);
}
