package uz.uniSport.uni_sport.domain.wellness;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.uniSport.uni_sport.domain.common.BaseEntity;

/**
 * Mashqlar katalogini (Exercise) ifodalovchi Entity.
 */
@Getter
@Setter
@Entity
@Table(name = "exercises")
public class Exercise extends BaseEntity {

    /**
     * Mashq nomi.
     */
    @Column(nullable = false, length = 150)
    private String name;

    /**
     * Maqsadli mushak guruhi (masalan, 'Orqa', 'Qorin').
     */
    @Column(name = "target_muscle_group", length = 100)
    private String targetMuscleGroup;

    /**
     * Mashq haqida batafsil tavsif.
     */
    @Column(columnDefinition = "TEXT")
    private String description;

    /**
     * Mashq videosi havolasi (YouTube yoki boshqa).
     */
    @Column(name = "video_url", length = 500)
    private String videoUrl;
}
