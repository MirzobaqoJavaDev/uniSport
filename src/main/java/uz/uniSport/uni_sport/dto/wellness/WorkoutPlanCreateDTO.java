package uz.uniSport.uni_sport.dto.wellness;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class WorkoutPlanCreateDTO {
    @NotNull(message = "User ID is required")
    private UUID userId;

    @NotBlank(message = "Name is required")
    private String name;

    private String description;
    private String difficultyLevel;

    private Set<UUID> exerciseIds;
}
