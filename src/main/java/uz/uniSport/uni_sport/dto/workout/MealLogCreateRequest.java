package uz.uniSport.uni_sport.dto.workout;

import lombok.Data;
import java.util.UUID;

@Data
public class MealLogCreateRequest {
    private UUID userId;
    private Integer calories;
}
