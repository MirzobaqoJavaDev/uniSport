package uz.uniSport.uni_sport.domain.payment;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.uniSport.uni_sport.domain.common.BaseEntity;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Obuna paketlarini (Subscription Plan) ifodalovchi Entity klassi.
 * Masalan: "Talabalar oylik obunasi", "Yillik ruxsatnoma".
 */
@Getter
@Setter
@Entity
@Table(name = "subscription_plans")
public class SubscriptionPlan extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(name = "duration_in_days", nullable = false)
    private Integer durationInDays;
}
