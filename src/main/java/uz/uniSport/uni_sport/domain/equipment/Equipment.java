package uz.uniSport.uni_sport.domain.equipment;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.uniSport.uni_sport.domain.common.BaseEntity;

import java.util.UUID;

/**
 * Sport inventarini (Equipment) ifodalovchi Entity klassi.
 * Masalan: Futbol to'pi, Tennis raketkasi, Gimnastika to'shagi.
 */
@Getter
@Setter
@Entity
@Table(name = "equipment")
public class Equipment extends BaseEntity {

    /**
     * Inventarning yagona identifikatori (ID).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Inventarning nomi (masalan, 'Voleybol to'pi').
     */
    @Column(nullable = false, length = 255)
    private String name;

    /**
     * Tizimda mavjud bo'lgan umumiy miqdor.
     */
    @Column(name = "total_quantity", nullable = false)
    private Integer totalQuantity;
}
