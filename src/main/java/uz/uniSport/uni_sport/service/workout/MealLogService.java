package uz.uniSport.uni_sport.service.workout;

import uz.uniSport.uni_sport.dto.workout.MealLogDto;

import java.util.List;
import java.util.UUID;

public interface MealLogService {
    MealLogDto logMeal(UUID userId, Integer calories);
    List<MealLogDto> getUserMealLogs(UUID userId);
}
