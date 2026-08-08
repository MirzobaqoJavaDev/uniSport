package uz.uniSport.uni_sport.dto.equipment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.uniSport.uni_sport.dto.auth.UserDto;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Inventar ijarasi (Rental) ma'lumotlarini tashuvchi DTO.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RentalDto {
    private UUID id;
    private UserDto user;
    private EquipmentDto equipment;
    private LocalDateTime rentedAt;
    private LocalDateTime returnedAt;
    private String status;
}
