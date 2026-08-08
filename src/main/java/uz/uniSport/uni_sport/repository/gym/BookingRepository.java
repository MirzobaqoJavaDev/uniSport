package uz.uniSport.uni_sport.repository.gym;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.uniSport.uni_sport.domain.gym.Booking;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Kortlarni band qilish jarayonlarini (Booking) boshqarish uchun Repository.
 */
@Repository
public interface BookingRepository extends JpaRepository<Booking, UUID> {
    
    /**
     * Foydalanuvchining barcha bandliklarini topish.
     * @param userId foydalanuvchi ID'si
     * @return bandliklar ro'yxati
     */
    List<Booking> findByUserId(UUID userId);

    /**
     * Berilgan vaqt oralig'ida aniq bir kort band qilinganligini tekshirish.
     * Overlapping (ustma-ust tushish) ni tekshirish uchun.
     * @param courtId kort ID'si
     * @param start boshlanish vaqti
     * @param end tugash vaqti
     * @return agar band bo'lsa true, aks holda false
     */
    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END FROM Booking b " +
           "WHERE b.court.id = :courtId AND b.status = 'ACTIVE' AND b.deleted = false " +
           "AND ((b.startTime < :end AND b.endTime > :start))")
    boolean isCourtBooked(@Param("courtId") UUID courtId, 
                          @Param("start") LocalDateTime start, 
                          @Param("end") LocalDateTime end);
}
