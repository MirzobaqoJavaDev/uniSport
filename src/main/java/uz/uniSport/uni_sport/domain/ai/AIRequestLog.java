package uz.uniSport.uni_sport.domain.ai;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.uniSport.uni_sport.domain.auth.User;
import uz.uniSport.uni_sport.domain.common.BaseEntity;

import java.util.UUID;

/**
 * AI modeliga yuborilgan barcha so'rovlar va ularning javoblarini (AI Request Log) saqlovchi Entity klassi.
 * Ushbu jadval tahlil qilish, xatolarni qidirish va tizim sifatini oshirish uchun ishlatiladi.
 */
@Getter
@Setter
@Entity
@Table(name = "ai_request_logs")
public class AIRequestLog extends BaseEntity {

    /**
     * AI so'rovi jurnalining yagona identifikatori (ID).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * So'rovni amalga oshirgan foydalanuvchi.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * So'rov qaysi shablon asosida amalga oshirilganligi (agar mavjud bo'lsa).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prompt_template_id")
    private PromptTemplate promptTemplate;

    /**
     * AI ga yuborilgan asl matn (PII ma'lumotlaridan tozalangan bo'lishi kutiladi).
     */
    @Column(columnDefinition = "TEXT", nullable = false)
    private String requestPayload;

    /**
     * AI modelidan qaytgan javob matni.
     */
    @Column(columnDefinition = "TEXT")
    private String responsePayload;

    /**
     * So'rovni amalga oshirish uchun ketgan vaqt (millisekundlarda).
     */
    @Column(name = "execution_time_ms")
    private Long executionTimeMs;
}
