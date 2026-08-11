package uz.uniSport.uni_sport.domain.integration;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.uniSport.uni_sport.domain.auth.User;
import uz.uniSport.uni_sport.domain.common.BaseEntity;

import java.time.LocalDateTime;

/**
 * Ruxsat yoki to'lovlar uchun dinamik QR kodlarni (Dynamic QR Code) ifodalovchi Entity.
 */
@Getter
@Setter
@Entity
@Table(name = "dynamic_qr_codes")
public class DynamicQRCode extends BaseEntity {

    /**
     * QR kodni yaratgan foydalanuvchi.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * QR kod ichidagi ma'lumot (encoded data).
     */
    @Column(name = "qr_data", nullable = false, length = 500)
    private String qrData;

    /**
     * QR kodning amal qilish muddati tugash vaqti.
     */
    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    /**
     * QR kod allaqachon foydalanilgan yoki yo'qligini bildiruvchi belgi.
     */
    @Column(name = "is_used", nullable = false)
    private Boolean isUsed = false;
}
