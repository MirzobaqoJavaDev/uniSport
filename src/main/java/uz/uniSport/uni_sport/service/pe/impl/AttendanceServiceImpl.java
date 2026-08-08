package uz.uniSport.uni_sport.service.pe.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.uniSport.uni_sport.domain.auth.User;
import uz.uniSport.uni_sport.domain.pe.Attendance;
import uz.uniSport.uni_sport.domain.pe.ClassSchedule;
import uz.uniSport.uni_sport.dto.pe.AttendanceDto;
import uz.uniSport.uni_sport.exception.ResourceNotFoundException;
import uz.uniSport.uni_sport.mapper.pe.PEMapper;
import uz.uniSport.uni_sport.repository.auth.UserRepository;
import uz.uniSport.uni_sport.repository.pe.AttendanceRepository;
import uz.uniSport.uni_sport.repository.pe.ClassScheduleRepository;
import uz.uniSport.uni_sport.service.pe.AttendanceService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final UserRepository userRepository;
    private final ClassScheduleRepository scheduleRepository;
    private final PEMapper peMapper;

    @Override
    @Transactional
    public AttendanceDto markAttendance(UUID userId, UUID scheduleId, String status) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Talaba topilmadi: " + userId));

        ClassSchedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ResourceNotFoundException("Dars jadvali topilmadi: " + scheduleId));

        Attendance attendance = new Attendance();
        attendance.setUser(user);
        attendance.setSchedule(schedule);
        attendance.setStatus(status); // "PRESENT", "ABSENT", "EXCUSED"
        attendance.setRecordedAt(LocalDateTime.now());

        Attendance saved = attendanceRepository.save(attendance);
        return peMapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AttendanceDto> getAttendanceByUser(UUID userId) {
        return attendanceRepository.findByUserId(userId).stream()
                .map(peMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AttendanceDto> getAttendanceBySchedule(UUID scheduleId) {
        return attendanceRepository.findByScheduleId(scheduleId).stream()
                .map(peMapper::toDto)
                .collect(Collectors.toList());
    }
}
