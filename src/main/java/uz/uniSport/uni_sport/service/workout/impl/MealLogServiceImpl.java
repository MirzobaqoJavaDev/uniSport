package uz.uniSport.uni_sport.service.workout.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.uniSport.uni_sport.domain.auth.User;
import uz.uniSport.uni_sport.domain.workout.MealLog;
import uz.uniSport.uni_sport.dto.workout.MealLogDto;
import uz.uniSport.uni_sport.exception.BusinessLogicException;
import uz.uniSport.uni_sport.exception.ResourceNotFoundException;
import uz.uniSport.uni_sport.mapper.workout.WorkoutMapper;
import uz.uniSport.uni_sport.repository.auth.UserRepository;
import uz.uniSport.uni_sport.repository.workout.MealLogRepository;
import uz.uniSport.uni_sport.service.workout.MealLogService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MealLogServiceImpl implements MealLogService {

    private final MealLogRepository mealLogRepository;
    private final UserRepository userRepository;
    private final WorkoutMapper workoutMapper;

    @Override
    @Transactional
    public MealLogDto logMeal(UUID userId, Integer calories) {
        if (calories == null || calories <= 0) {
            throw new BusinessLogicException("Kaloriya miqdori 0 dan katta bo'lishi kerak.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Foydalanuvchi topilmadi: " + userId));

        MealLog log = new MealLog();
        log.setUser(user);
        log.setCalories(calories);
        log.setLoggedAt(LocalDateTime.now());

        MealLog saved = mealLogRepository.save(log);
        return workoutMapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MealLogDto> getUserMealLogs(UUID userId) {
        return mealLogRepository.findByUserId(userId).stream()
                .map(workoutMapper::toDto)
                .collect(Collectors.toList());
    }
}
