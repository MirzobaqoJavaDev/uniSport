package uz.uniSport.uni_sport.domain.payment;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.uniSport.uni_sport.domain.auth.User;
import uz.uniSport.uni_sport.domain.common.BaseEntity;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * To'lov tranzaksiyalarini (Payment Transaction) ifodalovchi Entity.
 */
@Getter
@Setter
@Entity
@Table(name = "payment_transactions")
public class PaymentTransaction extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false, length = 3)
    private String currency;

    /**
     * Masalan: 'PENDING', 'SUCCESS', 'FAILED'
     */
    @Column(nullable = false, length = 50)
    private String status;

    /**
     * Takroriy to'lovlarni oldini olish uchun yagona kalit (Idempotency Key).
     */
    @Column(name = "idempotency_key", nullable = false, unique = true, length = 100)
    private String idempotencyKey;

    /**
     * Tashqi to'lov provayderi (Click, Payme) tomonidan berilgan tranzaksiya raqami.
     */
    @Column(name = "provider_transaction_id", length = 100)
    private String providerTransactionId;
}
