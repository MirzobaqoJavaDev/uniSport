package uz.uniSport.uni_sport.domain.auth;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.uniSport.uni_sport.domain.common.BaseEntity;

import java.util.HashSet;
import java.util.Set;

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
     * Rolning nomi (masalan, 'STUDENT', 'PE_FACULTY').
     * Bu nom tizimda takrorlanmas (unique) bo'lishi shart.
     */
    @Column(nullable = false, unique = true, length = 50)
    private String name;

    /**
     * Ushbu rolga biriktirilgan huquqlar (Permissions).
     */
    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "role_permissions",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<Permission> permissions = new HashSet<>();
}
