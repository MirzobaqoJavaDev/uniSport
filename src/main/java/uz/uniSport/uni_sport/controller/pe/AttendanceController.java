package uz.uniSport.uni_sport.controller.pe;

import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.uniSport.uni_sport.dto.pe.AttendanceDto;
import uz.uniSport.uni_sport.dto.pe.AttendanceMarkRequest;
import uz.uniSport.uni_sport.service.pe.AttendanceService;

import java.util.List;
import java.util.UUID;

@Tag(name = "Davomat", description = "Talabalarning jismoniy tarbiya darslariga davomatini boshqarish")
@RestController
@RequestMapping("/api/v1/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PostMapping
    @PreAuthorize("hasAuthority('ATTENDANCE_CREATE') or hasAuthority('ATTENDANCE_MANAGE') or hasAuthority('ATTENDANCE_QR_SCAN')")
    public ResponseEntity<AttendanceDto> markAttendance(@RequestBody AttendanceMarkRequest request) {
        AttendanceDto marked = attendanceService.markAttendance(
                request.getUserId(),
                request.getScheduleId(),
                request.getStatus()
        );
        return new ResponseEntity<>(marked, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userUuid}")
    @PreAuthorize("hasAuthority('ATTENDANCE_READ')")
    public ResponseEntity<List<AttendanceDto>> getAttendanceByUser(@PathVariable UUID userUuid) {
        return ResponseEntity.ok(attendanceService.getAttendanceByUser(userUuid));
    }

    @GetMapping("/schedule/{scheduleUuid}")
    @PreAuthorize("hasAuthority('ATTENDANCE_READ')")
    public ResponseEntity<List<AttendanceDto>> getAttendanceBySchedule(@PathVariable UUID scheduleUuid) {
        return ResponseEntity.ok(attendanceService.getAttendanceBySchedule(scheduleUuid));
    }
}
