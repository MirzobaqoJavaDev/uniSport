package uz.uniSport.uni_sport.domain.gym;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.uniSport.uni_sport.domain.common.BaseEntity;

import java.util.UUID;

/**
 * Sport inshootini (Facility) ifodalovchi Entity klassi.
 * Masalan: Sport majmuasi, Stadion, Suv havzasi (Basseyn).
 */
@Getter
@Setter
@Entity
@Table(name = "facilities")
public class Facility extends BaseEntity {

    /**
     * Inshootning yagona identifikatori (ID).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Sport inshootining nomi (masalan, 'Markaziy sport zali').
     */
    @Column(nullable = false, length = 100)
    private String name;

    /**
     * Sport inshooti haqida batafsil ma'lumot (tavsif).
     */
    @Column(columnDefinition = "TEXT")
    private String description;
}
