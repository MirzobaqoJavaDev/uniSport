package uz.uniSport.uni_sport.domain.equipment;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.uniSport.uni_sport.domain.common.BaseEntity;

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
