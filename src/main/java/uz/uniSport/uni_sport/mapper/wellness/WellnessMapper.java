package uz.uniSport.uni_sport.mapper.wellness;

import org.mapstruct.*;
import uz.uniSport.uni_sport.domain.wellness.Exercise;
import uz.uniSport.uni_sport.domain.wellness.WorkoutPlan;
import uz.uniSport.uni_sport.dto.wellness.ExerciseCreateDTO;
import uz.uniSport.uni_sport.dto.wellness.ExerciseResponseDTO;
import uz.uniSport.uni_sport.dto.wellness.ExerciseUpdateDTO;
import uz.uniSport.uni_sport.dto.wellness.WorkoutPlanCreateDTO;
import uz.uniSport.uni_sport.dto.wellness.WorkoutPlanResponseDTO;
import uz.uniSport.uni_sport.dto.wellness.WorkoutPlanUpdateDTO;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface WellnessMapper {

    @Mapping(source = "uuid", target = "id")
    ExerciseResponseDTO toDto(Exercise exercise);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    Exercise toEntity(ExerciseCreateDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    void updateEntity(ExerciseUpdateDTO dto, @MappingTarget Exercise exercise);

    @Mapping(source = "uuid", target = "id")
    @Mapping(source = "user.uuid", target = "userId")
    WorkoutPlanResponseDTO toDto(WorkoutPlan plan);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "exercises", ignore = true) // Handled in Service
    WorkoutPlan toEntity(WorkoutPlanCreateDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "exercises", ignore = true) // Handled in Service
    void updateEntity(WorkoutPlanUpdateDTO dto, @MappingTarget WorkoutPlan plan);
}
