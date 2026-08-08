package uz.uniSport.uni_sport.domain.integration;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.uniSport.uni_sport.domain.auth.User;
import uz.uniSport.uni_sport.domain.common.BaseEntity;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Ruxsat yoki to'lovlar uchun dinamik QR kodlarni (Dynamic QR Code) ifodalovchi Entity.
 */
@Getter
@Setter
@Entity
@Table(name = "dynamic_qr_codes")
public class DynamicQRCode extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "qr_data", nullable = false, length = 500)
    private String qrData;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @Column(name = "is_used", nullable = false)
    private Boolean isUsed = false;
}
