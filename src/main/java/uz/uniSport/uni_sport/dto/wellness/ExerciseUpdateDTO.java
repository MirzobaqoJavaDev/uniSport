package uz.uniSport.uni_sport.dto.wellness;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExerciseUpdateDTO {
    private String name;
    private String targetMuscleGroup;
    private String description;
    private String videoUrl;
}
