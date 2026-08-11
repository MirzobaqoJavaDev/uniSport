package uz.uniSport.uni_sport.domain.workout;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.uniSport.uni_sport.domain.auth.User;
import uz.uniSport.uni_sport.domain.common.BaseEntity;

import java.time.LocalDateTime;

/**
 * Foydalanuvchining ovqatlanish jurnali (Meal Log) va kaloriyalarni hisobga oluvchi Entity klassi.
 * Bu orqali talabaning kunlik ovqatlanishi qat'iy xavfsizlik kaloriyalari doirasida nazorat qilinadi.
 */
@Getter
@Setter
@Entity
@Table(name = "meal_logs")
public class MealLog extends BaseEntity {

    /**
     * Ovqatlanish jurnalini to'ldirgan foydalanuvchi (User).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Iste'mol qilingan umumiy kaloriya miqdori.
     */
    @Column(nullable = false)
    private Integer calories;

    /**
     * Ovqatlanish qachon bo'lib o'tganligi yoki qachon tizimga kiritilganligi.
     */
    @Column(name = "logged_at", nullable = false)
    private LocalDateTime loggedAt;
}
