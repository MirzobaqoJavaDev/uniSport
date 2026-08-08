package uz.uniSport.uni_sport.domain.equipment;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.uniSport.uni_sport.domain.auth.User;
import uz.uniSport.uni_sport.domain.common.BaseEntity;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Inventarni ijaraga berish (Rental) jarayonini ifodalovchi Entity klassi.
 * Bu jadvalda kim, qaysi inventarni, qachon olgani va qaytargani saqlanadi.
 */
@Getter
@Setter
@Entity
@Table(name = "rentals")
public class Rental extends BaseEntity {

    /**
     * Ijaraga olishning yagona identifikatori (ID).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Inventarni ijaraga olgan foydalanuvchi (User).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Ijaraga olingan inventar (Equipment).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_id", nullable = false)
    private Equipment equipment;

    /**
     * Ijaraga olingan vaqt.
     */
    @Column(name = "rented_at", nullable = false)
    private LocalDateTime rentedAt;

    /**
     * Inventar qaytarilgan vaqt.
     */
    @Column(name = "returned_at")
    private LocalDateTime returnedAt;

    /**
     * Ijaraning joriy holati (masalan, 'RENTED', 'RETURNED', 'OVERDUE').
     */
    @Column(nullable = false, length = 50)
    private String status;

    /**
     * Raqobatni boshqarish (Optimistic Locking) uchun versiya raqami.
     * Ikkita xodim bir vaqtda qaytarishni (return) rasmiylashtirmoqchi bo'lsa ziddiyatni oldini oladi.
     */
    @Version
    @Column(nullable = false)
    private Integer version;
}
