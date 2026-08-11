package uz.uniSport.uni_sport.domain.notification;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.uniSport.uni_sport.domain.auth.User;
import uz.uniSport.uni_sport.domain.common.BaseEntity;

/**
 * Foydalanuvchilarga yuborilgan bildirishnomalarni (Notification) ifodalovchi Entity.
 * referenceId — bildirishnoma bog'liq bo'lgan entity ning DB ichki id si (Long).
 */
@Getter
@Setter
@Entity
@Table(name = "notifications", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"reference_id", "type"})
})
public class Notification extends BaseEntity {

    /**
     * Bildirishnoma oluvchi foydalanuvchi.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient_id", nullable = false)
    private User recipient;

    /**
     * Bildirishnoma turi (masalan, REMINDER_24H).
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private NotificationType type;

    /**
     * Bildirishnoma bog'liq bo'lgan entity ning DB ichki identifikatori (Long).
     * Masalan: Booking.id (Long).
     */
    @Column(name = "reference_id", nullable = false)
    private Long referenceId;

    /**
     * Bildirishnoma yetkazish kanali (PUSH, SMS).
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private NotificationChannel channel;

    /**
     * Bildirishnoma holati (PENDING, SENT, FAILED).
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private NotificationStatus status;

    /**
     * Xato yuz berganda saqlanadigan xato xabari.
     */
    @Column(name = "error_message", length = 1000)
    private String errorMessage;

    /**
     * Optimistik blokirovka uchun versiya.
     */
    @Version
    @Column(nullable = false)
    private Integer version;
}
