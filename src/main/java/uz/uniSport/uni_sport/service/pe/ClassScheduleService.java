package uz.uniSport.uni_sport.service.pe;

import uz.uniSport.uni_sport.dto.pe.ClassScheduleDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ClassScheduleService {
    ClassScheduleDto createSchedule(UUID classId, LocalDateTime startTime, LocalDateTime endTime);
    List<ClassScheduleDto> getSchedulesBetween(LocalDateTime start, LocalDateTime end);
}
