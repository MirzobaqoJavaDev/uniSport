package uz.uniSport.uni_sport.domain.gym;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.uniSport.uni_sport.domain.common.BaseEntity;

import java.util.UUID;

/**
 * Sport inshooti ichidagi aniq bir kortni (Court) ifodalovchi Entity klassi.
 * Masalan: Tennis korti #1, Basketbol maydonchasi A.
 */
@Getter
@Setter
@Entity
@Table(name = "courts")
public class Court extends BaseEntity {

    /**
     * Kortning yagona identifikatori (ID).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Ushbu kort joylashgan asosiy sport inshooti (Facility).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "facility_id", nullable = false)
    private Facility facility;

    /**
     * Kortning nomi (masalan, 'Voleybol maydonchasi 2').
     */
    @Column(nullable = false, length = 100)
    private String name;
}
