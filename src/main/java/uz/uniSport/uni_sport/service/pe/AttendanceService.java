package uz.uniSport.uni_sport.service.pe;

import uz.uniSport.uni_sport.dto.pe.AttendanceDto;

import java.util.List;
import java.util.UUID;

public interface AttendanceService {
    AttendanceDto markAttendance(UUID userId, UUID scheduleId, String status);
    List<AttendanceDto> getAttendanceByUser(UUID userId);
    List<AttendanceDto> getAttendanceBySchedule(UUID scheduleId);
}
