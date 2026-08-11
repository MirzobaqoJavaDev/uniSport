package uz.uniSport.uni_sport.repository.pe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.uniSport.uni_sport.domain.pe.Attendance;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Davomat (Attendance) yozuvlarini boshqarish uchun Repository.
 */
@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    /**
     * Davomatni UUID bo'yicha qidirish (API so'rovlarida ishlatiladi).
     * @param uuid davomat tashqi identifikatori
     * @return topilgan davomat
     */
    Optional<Attendance> findByUuid(UUID uuid);

    /**
     * Ma'lum bir dars jadvaliga (schedule) tegishli bo'lgan barcha davomatlarni qidirish.
     * @param scheduleId dars jadvali DB ichki id si
     * @return davomatlar ro'yxati
     */
    List<Attendance> findByScheduleUuid(UUID scheduleId);

    /**
     * Ma'lum bir talabaning (User) barcha davomatlarini qidirish.
     * @param userId talaba DB ichki id si
     * @return davomatlar ro'yxati
     */
    List<Attendance> findByUserUuid(UUID userId);
}
