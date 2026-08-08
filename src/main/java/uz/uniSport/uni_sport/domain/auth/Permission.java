package uz.uniSport.uni_sport.domain.auth;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.uniSport.uni_sport.domain.common.BaseEntity;

/**
 * Tizimdagi huquqlarni (Permission) ifodalovchi Entity klassi.
 * Masalan: 'BOOKING_CREATE', 'USER_READ'.
 */
@Getter
@Setter
@Entity
@Table(name = "permissions")
public class Permission extends BaseEntity {

    /**
     * Huquqning yagona identifikatori (ID).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Huquqning nomi.
     * Bu nom tizimda takrorlanmas (unique) bo'lishi shart.
     */
    @Column(nullable = false, unique = true, length = 100)
    private String name;

    /**
     * Huquqning batafsil tavsifi.
     */
    @Column(columnDefinition = "TEXT")
    private String description;
}
