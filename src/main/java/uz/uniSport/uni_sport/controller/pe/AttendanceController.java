package uz.uniSport.uni_sport.controller.pe;

import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<AttendanceDto> markAttendance(@RequestBody AttendanceMarkRequest request) {
        AttendanceDto marked = attendanceService.markAttendance(
                request.getUserId(),
                request.getScheduleId(),
                request.getStatus()
        );
        return new ResponseEntity<>(marked, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AttendanceDto>> getAttendanceByUser(@PathVariable UUID userId) {
        return ResponseEntity.ok(attendanceService.getAttendanceByUser(userId));
    }

    @GetMapping("/schedule/{scheduleId}")
    public ResponseEntity<List<AttendanceDto>> getAttendanceBySchedule(@PathVariable UUID scheduleId) {
        return ResponseEntity.ok(attendanceService.getAttendanceBySchedule(scheduleId));
    }
}
