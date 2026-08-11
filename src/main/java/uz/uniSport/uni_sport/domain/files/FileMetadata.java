package uz.uniSport.uni_sport.domain.files;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.uniSport.uni_sport.domain.auth.User;
import uz.uniSport.uni_sport.domain.common.BaseEntity;

/**
 * Tizimga yuklangan fayllarning (hujjatlar, rasmlar) metama'lumotlarini (File Metadata) saqlovchi Entity klassi.
 * Haqiqiy fayllar MinIO (S3) da saqlanadi, bu jadvalda esa ularning qayerda joylashganligi (path) ko'rsatiladi.
 */
@Getter
@Setter
@Entity
@Table(name = "file_metadata")
public class FileMetadata extends BaseEntity {

    /**
     * Faylning asl nomi (masalan, 'profil_rasmi.jpg').
     */
    @Column(name = "original_name", nullable = false, length = 255)
    private String originalName;

    /**
     * MinIO yoki S3 da faylni topish uchun ishlatiladigan yo'l (path) va nom.
     * Bu tizim tomonidan yagona qilib generatsiya qilinadi.
     */
    @Column(name = "storage_path", nullable = false, unique = true, length = 500)
    private String storagePath;

    /**
     * Faylning MIME formati (masalan, 'image/jpeg', 'application/pdf').
     */
    @Column(name = "content_type", nullable = false, length = 100)
    private String contentType;

    /**
     * Faylning hajmi (baytlarda).
     */
    @Column(nullable = false)
    private Long size;

    /**
     * Ushbu faylni tizimga yuklagan foydalanuvchi.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uploaded_by", nullable = false)
    private User uploadedBy;
}
