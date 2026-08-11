package uz.uniSport.uni_sport.controller.workout;

import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.uniSport.uni_sport.dto.workout.MealLogCreateRequest;
import uz.uniSport.uni_sport.dto.workout.MealLogDto;
import uz.uniSport.uni_sport.service.workout.MealLogService;

import java.util.List;
import java.util.UUID;

@Tag(name = "Ovqatlanish", description = "Kunlik kaloriya va ovqatlanish ratsionini hisobga olish")
@RestController
@RequestMapping("/api/v1/meal-logs")
@RequiredArgsConstructor
public class MealLogController {

    private final MealLogService mealLogService;

    @PostMapping
    public ResponseEntity<MealLogDto> logMeal(@RequestBody MealLogCreateRequest request) {
        MealLogDto logged = mealLogService.logMeal(request.getUserId(), request.getCalories());
        return new ResponseEntity<>(logged, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<MealLogDto>> getUserMealLogs(@PathVariable UUID userId) {
        return ResponseEntity.ok(mealLogService.getUserMealLogs(userId));
    }
}
