package uz.uniSport.uni_sport.domain.pe;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.uniSport.uni_sport.domain.auth.User;
import uz.uniSport.uni_sport.domain.common.BaseEntity;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Talabalarning darsdagi davomatini (Attendance) ifodalovchi Entity klassi.
 * Bu orqali talabaning darsda qatnashganligi yoki yo'qligi qayd etiladi.
 */
@Getter
@Setter
@Entity
@Table(name = "attendances")
public class Attendance extends BaseEntity {

    /**
     * Davomat yozuvining yagona identifikatori (ID).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Davomati olinayotgan talaba (User).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Davomat olinayotgan aniq bir dars jadvali (ClassSchedule).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id", nullable = false)
    private ClassSchedule schedule;

    /**
     * Davomat holati (masalan, 'PRESENT', 'ABSENT', 'LATE').
     */
    @Column(nullable = false, length = 50)
    private String status;

    /**
     * Davomat olingan (qayd etilgan) vaqt (masalan, QR kod skaner qilingan vaqt).
     */
    @Column(name = "recorded_at", nullable = false)
    private LocalDateTime recordedAt;
}
