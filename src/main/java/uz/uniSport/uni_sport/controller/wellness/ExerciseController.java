package uz.uniSport.uni_sport.controller.wellness;

import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.uniSport.uni_sport.dto.wellness.ExerciseCreateDTO;
import uz.uniSport.uni_sport.dto.wellness.ExerciseResponseDTO;
import uz.uniSport.uni_sport.dto.wellness.ExerciseUpdateDTO;
import uz.uniSport.uni_sport.service.wellness.ExerciseService;

import java.util.List;
import java.util.UUID;

@Tag(name = "Mashqlar", description = "Sport mashqlari ro'yxati va ularning ma'lumotlarini boshqarish")
@RestController
@RequestMapping("/api/v1/exercises")
@RequiredArgsConstructor
public class ExerciseController {

    private final ExerciseService service;

    @GetMapping
    public ResponseEntity<List<ExerciseResponseDTO>> getAllExercises() {
        return ResponseEntity.ok(service.getAllExercises());
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ExerciseResponseDTO> getExerciseById(@PathVariable UUID uuid) {
        return ResponseEntity.ok(service.getExerciseById(uuid));
    }

    @PostMapping
    public ResponseEntity<ExerciseResponseDTO> createExercise(@Valid @RequestBody ExerciseCreateDTO createDTO) {
        return new ResponseEntity<>(service.createExercise(createDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<ExerciseResponseDTO> updateExercise(@PathVariable UUID uuid, @Valid @RequestBody ExerciseUpdateDTO updateDTO) {
        return ResponseEntity.ok(service.updateExercise(uuid, updateDTO));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteExercise(@PathVariable UUID uuid) {
        service.deleteExercise(uuid);
        return ResponseEntity.noContent().build();
    }
}
