package uz.uniSport.uni_sport.domain.pe;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.uniSport.uni_sport.domain.common.BaseEntity;

import java.time.LocalDateTime;

/**
 * Jismoniy tarbiya darslarining jadvallarini (Class Schedule) ifodalovchi Entity klassi.
 * Bu jadval orqali qaysi dars qachon bo'lishi aniqlanadi.
 */
@Getter
@Setter
@Entity
@Table(name = "class_schedules")
public class ClassSchedule extends BaseEntity {

    /**
     * Jadvalga tegishli bo'lgan asosiy JT darsi (PEClass).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id", nullable = false)
    private PEClass peClass;

    /**
     * Darsning boshlanish vaqti.
     */
    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    /**
     * Darsning tugash vaqti.
     */
    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;
}
