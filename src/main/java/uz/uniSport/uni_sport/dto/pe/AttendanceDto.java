package uz.uniSport.uni_sport.dto.pe;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.uniSport.uni_sport.dto.auth.UserDto;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Davomat (Attendance) yozuvlari uchun DTO.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceDto {
    private UUID id;
    private UserDto user;
    private ClassScheduleDto schedule;
    private String status;
    private LocalDateTime recordedAt;
}
