package uz.uniSport.uni_sport.domain.ai;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.uniSport.uni_sport.domain.common.BaseEntity;

import java.util.UUID;

/**
 * AI prompt shablonlarini (Prompt Template) ifodalovchi Entity klassi.
 * Bu jadvalda turli xil AI vazifalari uchun (masalan, mashg'ulot rejasi tuzish) 
 * asosiy prompt shablonlari saqlanadi.
 */
@Getter
@Setter
@Entity
@Table(name = "prompt_templates")
public class PromptTemplate extends BaseEntity {

    /**
     * Shablonning yagona identifikatori (ID).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Shablon nomi (masalan, 'WORKOUT_PLAN_GENERATION').
     */
    @Column(nullable = false, unique = true, length = 100)
    private String name;

    /**
     * Shablonning batafsil tavsifi.
     */
    @Column(columnDefinition = "TEXT")
    private String description;
    
    /**
     * Asosiy prompt matni (shablon o'zi).
     */
    @Column(name = "template_text", columnDefinition = "TEXT", nullable = false)
    private String templateText;
}
