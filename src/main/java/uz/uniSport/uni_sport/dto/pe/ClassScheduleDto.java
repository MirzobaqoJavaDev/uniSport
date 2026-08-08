package uz.uniSport.uni_sport.dto.pe;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Dars jadvallari (Class Schedule) uchun DTO.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassScheduleDto {
    private UUID id;
    private PEClassDto peClass;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
