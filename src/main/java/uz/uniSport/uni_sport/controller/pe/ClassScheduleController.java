package uz.uniSport.uni_sport.controller.pe;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.uniSport.uni_sport.dto.pe.ClassScheduleCreateRequest;
import uz.uniSport.uni_sport.dto.pe.ClassScheduleDto;
import uz.uniSport.uni_sport.service.pe.ClassScheduleService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/schedules")
@RequiredArgsConstructor
public class ClassScheduleController {

    private final ClassScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<ClassScheduleDto> createSchedule(@RequestBody ClassScheduleCreateRequest request) {
        ClassScheduleDto created = scheduleService.createSchedule(
                request.getPeClassId(),
                request.getStartTime(),
                request.getEndTime()
        );
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ClassScheduleDto>> getSchedules(
            @RequestParam LocalDateTime start,
            @RequestParam LocalDateTime end) {
        return ResponseEntity.ok(scheduleService.getSchedulesBetween(start, end));
    }
}
