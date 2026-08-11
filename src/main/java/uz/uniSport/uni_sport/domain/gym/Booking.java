package uz.uniSport.uni_sport.domain.gym;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.uniSport.uni_sport.domain.auth.User;
import uz.uniSport.uni_sport.domain.common.BaseEntity;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Kortlarni band qilish (Booking) jarayonini ifodalovchi Entity klassi.
 * Bu jadvalda band qilingan vaqt oraliqlari va holatlar saqlanadi.
 */
@Getter
@Setter
@Entity
@Table(name = "bookings")
public class Booking extends BaseEntity {

    /**
     * Kortni band qilgan foydalanuvchi (User).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Band qilingan kort (Court).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "court_id", nullable = false)
    private Court court;

    /**
     * Band qilishning boshlanish vaqti.
     */
    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    /**
     * Band qilishning tugash vaqti.
     */
    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    /**
     * Band qilishning joriy holati (masalan, 'ACTIVE', 'CANCELLED', 'COMPLETED').
     */
    @Column(nullable = false, length = 50)
    private String status;

    /**
     * Raqobatni boshqarish (Optimistic Locking) uchun versiya raqami.
     * Bir vaqtning o'zida ikkita foydalanuvchi bitta kortni band qilishga urinsa,
     * ushbu maydon ziddiyatni (conflict) aniqlashga yordam beradi.
     */
    @Version
    @Column(nullable = false)
    private Integer version;

    /**
     * Ushbu bandlikni yaratgan administrator yoki xodim uuid si (ixtiyoriy).
     * User.uuid ga ishora qiladi.
     */
    @Column(name = "created_by")
    private UUID createdBy;

    /**
     * Ushbu bandlikni oxirgi marta o'zgartirgan administrator yoki xodim uuid si (ixtiyoriy).
     * User.uuid ga ishora qiladi.
     */
    @Column(name = "updated_by")
    private UUID updatedBy;
}
