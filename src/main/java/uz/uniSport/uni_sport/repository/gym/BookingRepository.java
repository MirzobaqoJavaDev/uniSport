package uz.uniSport.uni_sport.repository.gym;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.uniSport.uni_sport.domain.gym.Booking;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Kortlarni band qilish jarayonlarini (Booking) boshqarish uchun Repository.
 */
@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    /**
     * Bandlikni UUID bo'yicha qidirish (API so'rovlarida ishlatiladi).
     * @param uuid bandlik tashqi identifikatori
     * @return topilgan bandlik
     */
    Optional<Booking> findByUuid(UUID uuid);

    /**
     * Foydalanuvchining barcha bandliklarini topish (DB ichki id bo'yicha).
     * @param userId foydalanuvchi DB ichki id si
     * @return bandliklar ro'yxati
     */
    List<Booking> findByUserUuid(UUID userId);

    /**
     * Berilgan vaqt oralig'ida aniq bir kort band qilinganligini tekshirish.
     * Overlapping (ustma-ust tushish) ni tekshirish uchun.
     * @param courtId kort DB ichki id si
     * @param start boshlanish vaqti
     * @param end tugash vaqti
     * @return agar band bo'lsa true, aks holda false
     */
    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END FROM Booking b " +
           "WHERE b.court.id = :courtId AND b.status = 'ACTIVE' AND b.deleted = false " +
           "AND ((b.startTime < :end AND b.endTime > :start))")
    boolean isCourtBooked(@Param("courtId") Long courtId,
                          @Param("start") LocalDateTime start,
                          @Param("end") LocalDateTime end);

    /**
     * Eslatma yuborish uchun mos keladigan bandliklarni topish.
     * ACTIVE holatdagi, vaqti yetgan va hali ushbu turdagi eslatma yuborilmagan bandliklar olinadi.
     */
    @Query("SELECT b FROM Booking b WHERE b.status = 'ACTIVE' AND b.deleted = false " +
           "AND b.startTime > :now AND b.startTime <= :targetTime " +
           "AND NOT EXISTS (SELECT 1 FROM Notification n WHERE n.referenceId = b.id AND n.type = :type)")
    List<Booking> findEligibleBookingsForReminder(@Param("now") LocalDateTime now,
                                                  @Param("targetTime") LocalDateTime targetTime,
                                                  @Param("type") uz.uniSport.uni_sport.domain.notification.NotificationType type);
}
