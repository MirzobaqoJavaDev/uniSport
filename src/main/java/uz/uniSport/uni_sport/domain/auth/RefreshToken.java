package uz.uniSport.uni_sport.domain.auth;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Foydalanuvchining sessiyasini uzaytirish uchun ishlatiladigan Refresh Token larni ifodalovchi Entity klassi.
 * Qisqa muddatli JWT token yaroqsiz bo'lib qolganida yangisini olish uchun ishlatiladi.
 * Bu entity BaseEntity dan voris olmaydi — id Long, uuid yo'q, audit maydonlar alohida.
 */
@Getter
@Setter
@Entity
@Table(name = "refresh_tokens")
public class RefreshToken {

    /**
     * Refresh token yozuvining yagona identifikatori (Long, DB ichki).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Ushbu tokenga tegishli bo'lgan foydalanuvchi.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Yagona, noyob va xavfsiz tarzda generatsiya qilingan token satri (string).
     */
    @Column(nullable = false, unique = true)
    private String token;

    /**
     * Tokenning yaroqlilik muddati tugash vaqti.
     */
    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;
}
