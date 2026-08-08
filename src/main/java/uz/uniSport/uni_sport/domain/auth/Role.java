package uz.uniSport.uni_sport.domain.auth;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.uniSport.uni_sport.domain.common.BaseEntity;

/**
 * Tizimdagi rollarni (Role) ifodalovchi Entity klassi.
 * Har bir foydalanuvchi ma'lum bir rolga ega bo'ladi (masalan, STUDENT, ADMIN).
 */
@Getter
@Setter
@Entity
@Table(name = "roles")
public class Role extends BaseEntity {

    /**
     * Rolning yagona identifikatori (ID).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Rolning nomi (masalan, 'STUDENT', 'PE_FACULTY').
     * Bu nom tizimda takrorlanmas (unique) bo'lishi shart.
     */
    @Column(nullable = false, unique = true, length = 50)
    private String name;
}
