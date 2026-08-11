package uz.uniSport.uni_sport.mapper.workout;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import uz.uniSport.uni_sport.domain.workout.MealLog;
import uz.uniSport.uni_sport.dto.workout.MealLogDto;
import uz.uniSport.uni_sport.mapper.auth.AuthMapper;

/**
 * Workout moduli uchun MapStruct mapper interfeysi.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {AuthMapper.class})
public interface WorkoutMapper {
    @Mapping(source = "uuid", target = "id")
    MealLogDto toDto(MealLog mealLog);
}
