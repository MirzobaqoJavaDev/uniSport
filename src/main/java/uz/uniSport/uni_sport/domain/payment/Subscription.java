package uz.uniSport.uni_sport.domain.payment;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.uniSport.uni_sport.domain.auth.User;
import uz.uniSport.uni_sport.domain.common.BaseEntity;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Foydalanuvchining faol yoki yakunlangan obunalarini (Subscription) ifodalovchi Entity.
 */
@Getter
@Setter
@Entity
@Table(name = "subscriptions")
public class Subscription extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id", nullable = false)
    private SubscriptionPlan plan;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    /**
     * Masalan: 'ACTIVE', 'EXPIRED', 'CANCELLED'
     */
    @Column(nullable = false, length = 50)
    private String status;
}
