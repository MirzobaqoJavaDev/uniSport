package uz.uniSport.uni_sport.domain.payment;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.uniSport.uni_sport.domain.common.BaseEntity;

import java.math.BigDecimal;

/**
 * Obuna paketlarini (Subscription Plan) ifodalovchi Entity klassi.
 * Masalan: "Talabalar oylik obunasi", "Yillik ruxsatnoma".
 */
@Getter
@Setter
@Entity
@Table(name = "subscription_plans")
public class SubscriptionPlan extends BaseEntity {

    /**
     * Obuna paketi nomi.
     */
    @Column(nullable = false, length = 100)
    private String name;

    /**
     * Obuna paketi tavsifi.
     */
    @Column(columnDefinition = "TEXT")
    private String description;

    /**
     * Obuna paketi narxi.
     */
    @Column(nullable = false)
    private BigDecimal price;

    /**
     * Obuna davomiyligi (kunlarda).
     */
    @Column(name = "duration_in_days", nullable = false)
    private Integer durationInDays;
}
