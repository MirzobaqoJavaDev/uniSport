package uz.uniSport.uni_sport.domain.gym;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.uniSport.uni_sport.domain.auth.Role;
import uz.uniSport.uni_sport.domain.common.BaseEntity;

/**
 * Band qilish kvotalarini (Quota) ifodalovchi Entity klassi.
 * Masalan: 'STUDENT' roli uchun bir vaqtning o'zida ko'pi bilan 2 ta faol bandlik (active bookings).
 */
@Getter
@Setter
@Entity
@Table(name = "quotas")
public class Quota extends BaseEntity {

    /**
     * Ushbu kvota qaysi rolga tegishli ekanligi.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    /**
     * Bir vaqtning o'zida maksimal nechta faol bandlik bo'lishi mumkinligi (masalan, 2).
     */
    @Column(name = "max_active_bookings", nullable = false)
    private Integer maxActiveBookings;

    /**
     * Bir haftada maksimal nechta bandlik qilish mumkinligi (ixtiyoriy).
     */
    @Column(name = "max_bookings_per_week")
    private Integer maxBookingsPerWeek;

    /**
     * Optimistik blokirovka (Optimistic Locking) uchun versiya.
     */
    @Version
    @Column(nullable = false)
    private Integer version;
}
