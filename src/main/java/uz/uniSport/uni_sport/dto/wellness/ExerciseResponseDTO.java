package uz.uniSport.uni_sport.dto.wellness;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ExerciseResponseDTO {
    private UUID id;
    private String name;
    private String targetMuscleGroup;
    private String description;
    private String videoUrl;
}
