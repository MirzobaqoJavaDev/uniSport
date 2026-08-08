package uz.uniSport.uni_sport.controller.wellness;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.uniSport.uni_sport.dto.wellness.WorkoutPlanCreateDTO;
import uz.uniSport.uni_sport.dto.wellness.WorkoutPlanResponseDTO;
import uz.uniSport.uni_sport.dto.wellness.WorkoutPlanUpdateDTO;
import uz.uniSport.uni_sport.service.wellness.WorkoutPlanService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/workout-plans")
@RequiredArgsConstructor
public class WorkoutPlanController {

    private final WorkoutPlanService service;

    @GetMapping
    public ResponseEntity<List<WorkoutPlanResponseDTO>> getAllWorkoutPlans() {
        return ResponseEntity.ok(service.getAllWorkoutPlans());
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkoutPlanResponseDTO> getWorkoutPlanById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getWorkoutPlanById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<WorkoutPlanResponseDTO>> getWorkoutPlansByUserId(@PathVariable UUID userId) {
        return ResponseEntity.ok(service.getWorkoutPlansByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<WorkoutPlanResponseDTO> createWorkoutPlan(@Valid @RequestBody WorkoutPlanCreateDTO createDTO) {
        return new ResponseEntity<>(service.createWorkoutPlan(createDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkoutPlanResponseDTO> updateWorkoutPlan(@PathVariable UUID id, @Valid @RequestBody WorkoutPlanUpdateDTO updateDTO) {
        return ResponseEntity.ok(service.updateWorkoutPlan(id, updateDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkoutPlan(@PathVariable UUID id) {
        service.deleteWorkoutPlan(id);
        return ResponseEntity.noContent().build();
    }
}
