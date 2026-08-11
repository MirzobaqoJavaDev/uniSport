package uz.uniSport.uni_sport.domain.ai;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.uniSport.uni_sport.domain.auth.User;
import uz.uniSport.uni_sport.domain.common.BaseEntity;

import java.time.LocalDateTime;

/**
 * Foydalanuvchilar tomonidan AI/LLM dan foydalanish tokenlari hisobini yurituvchi Entity.
 */
@Getter
@Setter
@Entity
@Table(name = "token_usage_logs")
public class TokenUsageLog extends BaseEntity {

    /**
     * Tokendan foydalangan foydalanuvchi.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * So'rov turi: 'DIET_GENERATION', 'WORKOUT_GENERATION', 'CHAT'.
     */
    @Column(name = "request_type", nullable = false, length = 100)
    private String requestType;

    /**
     * Sarflangan tokenlar soni.
     */
    @Column(name = "tokens_used", nullable = false)
    private Integer tokensUsed;

    /**
     * So'rov yuborilgan vaqt.
     */
    @Column(nullable = false)
    private LocalDateTime timestamp;
}
