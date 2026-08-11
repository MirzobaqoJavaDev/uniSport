package uz.uniSport.uni_sport.domain.ai;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.uniSport.uni_sport.domain.common.BaseEntity;

/**
 * AI prompt shablonining aniq bir versiyasini (Prompt Version) ifodalovchi Entity klassi.
 * Bu jadvalda prompt matnlari versiyalanib saqlanadi, shunday qilib eskisini o'zgartirmasdan
 * yangi versiyalarni sinab ko'rish imkoniyati yaratiladi.
 */
@Getter
@Setter
@Entity
@Table(name = "prompt_versions")
public class PromptVersion extends BaseEntity {

    /**
     * Qaysi shablonga tegishli ekanligini ko'rsatuvchi havola.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "template_id", nullable = false)
    private PromptTemplate template;

    /**
     * Versiya raqami (masalan, 1.0, 1.1, 2.0).
     */
    @Column(name = "version_number", nullable = false, length = 50)
    private String versionNumber;

    /**
     * AI uchun yuboriladigan prompt matni (System yoki User prompt).
     */
    @Column(name = "prompt_text", columnDefinition = "TEXT", nullable = false)
    private String promptText;

    /**
     * Ushbu versiya faol (active) yoki yo'qligini bildiradi.
     * Bitta shablon uchun faqat bitta versiya faol bo'lishi kerak.
     */
    @Column(name = "is_active", nullable = false)
    private boolean isActive = false;
}
