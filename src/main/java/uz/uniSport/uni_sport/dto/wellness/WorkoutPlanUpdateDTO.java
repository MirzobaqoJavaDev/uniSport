package uz.uniSport.uni_sport.dto.wellness;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class WorkoutPlanUpdateDTO {
    private String name;
    private String description;
    private String difficultyLevel;
    private Set<UUID> exerciseIds;
}
