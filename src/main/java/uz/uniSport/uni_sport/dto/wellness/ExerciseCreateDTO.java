package uz.uniSport.uni_sport.dto.wellness;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExerciseCreateDTO {
    @NotBlank(message = "Name is required")
    private String name;

    private String targetMuscleGroup;
    private String description;
    private String videoUrl;
}
