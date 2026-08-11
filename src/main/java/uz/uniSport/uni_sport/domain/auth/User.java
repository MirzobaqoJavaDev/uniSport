package uz.uniSport.uni_sport.domain.auth;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.uniSport.uni_sport.domain.common.BaseEntity;

/**
 * Tizim foydalanuvchisini ifodalovchi Entity klassi.
 * Barcha foydalanuvchilar (talabalar, xodimlar, adminlar) ushbu jadvalda saqlanadi.
 */
@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    /**
     * Foydalanuvchining elektron pochta manzili.
     * Tizimga kirish uchun login sifatida ishlatiladi va yagona (unique) bo'lishi shart.
     */
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * Foydalanuvchi paroli (shifrlangan - hashed holatda saqlanadi).
     */
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    /**
     * Foydalanuvchining ismi.
     */
    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    /**
     * Foydalanuvchining familiyasi.
     */
    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    /**
     * Foydalanuvchiga tegishli bo'lgan rol (Role).
     * Har bir foydalanuvchi faqat bitta asosiy rolga ega deb qabul qilinadi.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;
}
