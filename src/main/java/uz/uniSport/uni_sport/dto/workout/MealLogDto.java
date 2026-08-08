package uz.uniSport.uni_sport.dto.workout;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.uniSport.uni_sport.dto.auth.UserDto;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Ovqatlanish jurnali (Meal Log) uchun DTO.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MealLogDto {
    private UUID id;
    private UserDto user;
    private Integer calories;
    private LocalDateTime loggedAt;
}
