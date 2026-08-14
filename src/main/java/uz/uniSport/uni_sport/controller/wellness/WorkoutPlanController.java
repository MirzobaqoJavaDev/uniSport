package uz.uniSport.uni_sport.controller.wellness;

import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Mashg'ulotlar Rejasi", description = "Foydalanuvchilarning shaxsiy mashg'ulot rejalarini tuzish va boshqarish")
@RestController
@RequestMapping("/api/v1/workout-plans")
@RequiredArgsConstructor
public class WorkoutPlanController {

    private final WorkoutPlanService service;

    @GetMapping
    public ResponseEntity<List<WorkoutPlanResponseDTO>> getAllWorkoutPlans() {
        return ResponseEntity.ok(service.getAllWorkoutPlans());
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<WorkoutPlanResponseDTO> getWorkoutPlanById(@PathVariable UUID uuid) {
        return ResponseEntity.ok(service.getWorkoutPlanById(uuid));
    }

    @GetMapping("/user/{userUuid}")
    public ResponseEntity<List<WorkoutPlanResponseDTO>> getWorkoutPlansByUserId(@PathVariable UUID userUuid) {
        return ResponseEntity.ok(service.getWorkoutPlansByUserId(userUuid));
    }

    @PostMapping
    public ResponseEntity<WorkoutPlanResponseDTO> createWorkoutPlan(@Valid @RequestBody WorkoutPlanCreateDTO createDTO) {
        return new ResponseEntity<>(service.createWorkoutPlan(createDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<WorkoutPlanResponseDTO> updateWorkoutPlan(@PathVariable UUID uuid, @Valid @RequestBody WorkoutPlanUpdateDTO updateDTO) {
        return ResponseEntity.ok(service.updateWorkoutPlan(uuid, updateDTO));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteWorkoutPlan(@PathVariable UUID uuid) {
        service.deleteWorkoutPlan(uuid);
        return ResponseEntity.noContent().build();
    }
}
