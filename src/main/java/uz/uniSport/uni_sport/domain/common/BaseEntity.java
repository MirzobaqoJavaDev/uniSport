package uz.uniSport.uni_sport.domain.common;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Barcha ma'lumotlar bazasi jadvallari (entities) uchun asosiy (base) klass.
 * Bu klass orqali barcha entitilarga quyidagilar qo'shiladi:
 * - id (Long) — faqat ma'lumotlar bazasi ichki foydalanishi uchun (primary key, foreign key).
 * - uuid (UUID) — frontend va API bilan ishlash uchun. Hech qachon requestda kelmaydi,
 *                  faqat responseda qaytariladi.
 * - createdAt, updatedAt — audit maydonlar.
 * - deleted — qo'shni (soft) o'chirish belgisi.
 */
@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {

    /**
     * Ma'lumotlar bazasi ichki identifikatori (primary key).
     * Faqat DB ichida ishlatiladi (JOIN, Foreign Key). Frontga chiqarilmaydi.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Tashqi (public) identifikator — frontend va API uchun.
     * Registratsiya vaqtida avtomatik generatsiya qilinadi.
     * Frontdan hech qachon kelmaydi, faqat responseda qaytariladi.
     */
    @Column(nullable = false, unique = true, updatable = false)
    private UUID uuid;

    /**
     * Yozuv yaratilgan vaqtni saqlovchi ustun (column).
     */
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * Yozuv oxirgi marta o'zgartirilgan vaqtni saqlovchi ustun.
     */
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    /**
     * Yozuv o'chirilganligini (soft delete) bildiruvchi belgi (flag).
     * Agar 'true' bo'lsa, yozuv o'chirilgan hisoblanadi.
     */
    @Column(name = "deleted", nullable = false)
    private boolean deleted = false;

    /**
     * Obyekt ma'lumotlar bazasiga birinchi marta saqlanishidan oldin avtomatik ishga tushadi.
     * Uuid, yaratilgan va yangilangan vaqtlarni hozirgi vaqtga o'rnatadi.
     */
    @PrePersist
    protected void onCreate() {
        if (this.uuid == null) {
            this.uuid = UUID.randomUUID();
        }
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Obyekt ma'lumotlar bazasida yangilanishidan oldin avtomatik ishga tushadi.
     * Yangilangan vaqtni hozirgi vaqtga o'rnatadi.
     */
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
