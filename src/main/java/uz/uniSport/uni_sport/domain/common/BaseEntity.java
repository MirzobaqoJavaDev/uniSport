package uz.uniSport.uni_sport.domain.common;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

/**
 * Barcha ma'lumotlar bazasi jadvallari (entities) uchun asosiy (base) klass.
 * Bu klass orqali barcha entitilarga yaratilgan va yangilangan vaqtlar (audit fields) 
 * hamda jismoniy o'chirmaslik (soft delete) xususiyatlari qo'shiladi.
 */
@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {

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
     * Yaratilgan va yangilangan vaqtlarni hozirgi vaqtga o'rnatadi.
     */
    @PrePersist
    protected void onCreate() {
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
